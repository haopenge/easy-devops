package com.easy.api.service;

import com.alibaba.fastjson.JSON;
import com.easy.api.domain.entity.GrayEnvEntity;
import com.easy.api.domain.enumx.FailureEnum;
import com.easy.api.domain.enumx.StateEnum;
import com.easy.api.domain.vo.GrayEnvExtObjVo;
import com.easy.api.domain.vo.response.GrayEnvResponseVo;
import com.easy.api.exception.ServiceException;
import com.easy.api.mapper.GrayEnvMapper;
import com.easy.api.util.CommandUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class GrayService {

    @Value("${k8s.project_clone_path:/root/easy-gray}")
    private String k8sProjectClonePath;

    @Autowired
    private GrayEnvMapper grayEnvMapper;

    @Autowired
    private GithubService githubService;

    @Autowired
    private K8sService k8sService;

    public Integer addGrayEnv(String name, LocalDateTime expireTime) {
        GrayEnvEntity saveEntity = new GrayEnvEntity();
        saveEntity.setName(name);
        saveEntity.setExpireTime(expireTime);

        grayEnvMapper.insertSelective(saveEntity);
        return saveEntity.getId();
    }

    /**
     * 添加项目到灰度环境
     */
    public void addProjectToGrayEnv(Integer id, String name, String branch,String cloneUrl,String packagePath,String gitName) {
        GrayEnvEntity grayEnvEntity = grayEnvMapper.selectByPrimaryKey(id);
        if(Objects.isNull(grayEnvEntity) || !Objects.equals(grayEnvEntity.getState(), StateEnum.NORMAL.getValue())){
            throw new ServiceException(FailureEnum.GRAY_ENV_NOT_EXIST);
        }

        List<GrayEnvExtObjVo> extObjVoList = new ArrayList<>();
        String extObj = grayEnvEntity.getExtObj();
        if(StringUtils.isNotBlank(extObj)){
            extObjVoList = JSON.parseArray(extObj,GrayEnvExtObjVo.class);
            Optional<GrayEnvExtObjVo> voOptional = extObjVoList.stream().filter(loopVo -> Objects.equals(loopVo.getName(), name)).findFirst();
            voOptional.ifPresent(extObjVoList::remove);
        }

        // 更新 此项目信息
        // 封装 灰度扩展项目Vo
        GrayEnvExtObjVo extObjVo = new GrayEnvExtObjVo();
        extObjVo.setName(name);
        extObjVo.setBranch(branch);
        extObjVo.setGitName(gitName);
        extObjVo.setCloneUrl(cloneUrl);
        extObjVo.setPackagePath(packagePath);
        extObjVoList.add(extObjVo);

        GrayEnvEntity updateEntity = new GrayEnvEntity();
        updateEntity.setId(id);
        updateEntity.setExtObj(JSON.toJSONString(extObjVoList));

        grayEnvMapper.updateByPrimaryKeySelective(updateEntity);
    }

    public List<GrayEnvResponseVo> findAllGrayEnv() {
        List<GrayEnvEntity> grayEnvEntityList = grayEnvMapper.selectByState(StateEnum.NORMAL.getValue());
        if(CollectionUtils.isEmpty(grayEnvEntityList)){
            return Collections.emptyList();
        }

        List<GrayEnvResponseVo> returnList = new ArrayList<>();
        for (GrayEnvEntity loopEntity : grayEnvEntityList) {
            GrayEnvResponseVo envVo = new GrayEnvResponseVo();
            BeanUtils.copyProperties(loopEntity,envVo);
            String extObj = loopEntity.getExtObj();

            if(StringUtils.isNotBlank(extObj)){
                envVo.setExtObjList(JSON.parseArray(extObj,GrayEnvExtObjVo.class));
            }
            returnList.add(envVo);
        }
        return returnList;
    }

    public void runProjectInGrayEnv(Integer id, String projectName) {
        // 获取灰度环境项目
        GrayEnvEntity grayEnvEntity = grayEnvMapper.selectByPrimaryKey(id);
        if(Objects.isNull(grayEnvEntity) || !Objects.equals(grayEnvEntity.getState(), StateEnum.NORMAL.getValue())){
            throw new ServiceException(FailureEnum.GIT_FETCH_EXCEPTION);
        }

        List<GrayEnvExtObjVo> extObjVoList = null;
        String extObj = grayEnvEntity.getExtObj();
        if(StringUtils.isBlank(extObj)){
            throw new ServiceException(FailureEnum.GRAY_ENV_PROJECT_NOT_EXIST);
        }
        extObjVoList = JSON.parseArray(extObj,GrayEnvExtObjVo.class);
        Optional<GrayEnvExtObjVo> extObjOptional = extObjVoList.stream().filter(loopVo -> Objects.equals(loopVo.getName(), projectName)).findFirst();
        if(!extObjOptional.isPresent()){
            throw new ServiceException(FailureEnum.GRAY_ENV_PROJECT_NOT_EXIST);
        }
        GrayEnvExtObjVo extObjVo = extObjOptional.get();
        String cloneUrl = extObjVo.getCloneUrl();
        String name = extObjVo.getName();
        String gitName = extObjVo.getGitName();
        String branch = extObjVo.getBranch();
        String packagePath = extObjVo.getPackagePath();

        // 子项目 兼容处理
        String gitClonePath = k8sProjectClonePath + File.separator + gitName;
        String executePath = gitClonePath + (StringUtils.isBlank(packagePath) ? "" : File.separator + packagePath);

        // TODO 拉取代码
       // githubService.download(cloneUrl,branch,gitClonePath);

        // 文件复制 处理
        copyFileToExecutePath(executePath,"deployment.yaml");
        copyFileToExecutePath(executePath,"ali-docker-auth.yaml");
        copyFileToExecutePath(executePath, "start.sh");

        String podEnv = grayEnvEntity.getName().toLowerCase();
        String version = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmmss"));

        // 构建镜像
        String startFilePath = executePath + File.separator + "start.sh";
        String commandLineStr = String.format("sh %s %s %s %s %s",startFilePath,podEnv,name,executePath,version);
        log.info("runProjectInGrayEnv cmd : {}",commandLineStr);
        CommandLine commandLine = CommandLine.parse(commandLineStr);

        CommandUtil.execCmdWithoutResult(commandLine,600);

        // 发布服务
        try {
            k8sService.createNamespace(podEnv.toLowerCase());

            String dockerAuthFilePath = executePath + File.separator + "ali-docker-auth.yaml";
            k8sService.createSecrets(podEnv,dockerAuthFilePath);

            String deploymentFilePath = executePath + File.separator + "deployment.yaml";
            k8sService.createDeployment(podEnv,deploymentFilePath);
        } catch (Exception e) {
            log.error("k8s deployment error ,",e);
            throw new ServiceException(FailureEnum.K8S_DEPLOY_deployment);
        }
    }

    private void copyFileToExecutePath(String executePath,String fileName) {
        String filePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("k8s-gray/" + fileName)).getPath();
        copy(filePath, executePath + File.separator + fileName);
    }

    public void copy(String resourcePath,String targetPath){
        try(
                FileInputStream fis = new FileInputStream(resourcePath);
                FileOutputStream fos = new FileOutputStream(targetPath)
                ){
            IOUtils.copy(fis,fos);
        }catch (Exception e){
            throw new ServiceException(FailureEnum.FILE_COPY_EXCEPTION);
        }
    }
}
