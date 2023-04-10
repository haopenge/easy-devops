package com.easy.api.service;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.easy.api.domain.entity.GrayEnvEntity;
import com.easy.api.domain.entity.GrayEnvEntityExample;
import com.easy.api.domain.enumx.FailureEnum;
import com.easy.api.domain.enumx.StateEnum;
import com.easy.api.domain.vo.GrayEnvExtObjVo;
import com.easy.api.domain.vo.response.GitProjectResponseVo;
import com.easy.api.domain.vo.response.GrayEnvResponseVo;
import com.easy.api.exception.ServiceException;
import com.easy.api.mapper.GrayEnvEntityMapper;
import com.easy.core.util.CmdUtil;
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

@Slf4j
@Service
public class GrayService {

    @Value("${k8s.project_clone_path:}")
    private String k8sProjectClonePath;

    @Autowired
    private GrayEnvEntityMapper grayEnvMapper;

    @Autowired
    private K8sService k8sService;

    @Autowired
    private IGitService gitService;

    @Value("${docker.repository_pwd:}")
    private String dockerRepositoryPwd;

    public Integer addGrayEnv(String name, Date expireTime) {
        GrayEnvEntity saveEntity = new GrayEnvEntity();
        saveEntity.setName(name);
        saveEntity.setExpireTime(expireTime);

        grayEnvMapper.insertSelective(saveEntity);

        try {
            k8sService.createNamespace(name);
        } catch (Exception e) {
            throw new ServiceException(FailureEnum.K8S_NAMESPACE_CREATE_ERROR);
        }

        return saveEntity.getId();
    }

    public void deleteById(Integer id) {
        GrayEnvEntity grayEnvEntity = grayEnvMapper.selectByPrimaryKey(id);
        if (Objects.isNull(grayEnvEntity)) {
            return;
        }
        grayEnvMapper.deleteByPrimaryKey(id);
        try {
            k8sService.createNamespace(grayEnvEntity.getName());
        } catch (Exception e) {
            throw new ServiceException(FailureEnum.K8S_NAMESPACE_CREATE_ERROR);
        }
    }

    /**
     * 添加项目到灰度环境
     */
    public void addProjectToGrayEnv(Integer id, String fullName, String subProjectPath, String branchName) {
        GrayEnvEntity grayEnvEntity = grayEnvMapper.selectByPrimaryKey(id);
        if (Objects.isNull(grayEnvEntity) || !Objects.equals(grayEnvEntity.getState(), StateEnum.NORMAL.getValue())) {
            throw new ServiceException(FailureEnum.GRAY_ENV_NOT_EXIST);
        }

        GitProjectResponseVo gitProjectResponseVo = gitService.findRepositoryByFullName(fullName);
        if (Objects.isNull(gitProjectResponseVo)) {
            throw new ServiceException(FailureEnum.GIT_FETCH_EXCEPTION);
        }

        String projectId = gitProjectResponseVo.getName();
        if (StringUtils.isNotBlank(subProjectPath)) {
            int i = subProjectPath.lastIndexOf("/");
            projectId = (i == -1 ? subProjectPath : subProjectPath.substring(i + 1));
        }

        List<GrayEnvExtObjVo> extObjVoList = new ArrayList<>();
        String extObj = grayEnvEntity.getExtObj();
        if (StringUtils.isNotBlank(extObj)) {
            extObjVoList = JSON.parseArray(extObj, GrayEnvExtObjVo.class);
            String finalProjectId = projectId;
            Optional<GrayEnvExtObjVo> voOptional = extObjVoList.stream().filter(loopVo -> Objects.equals(loopVo.getId(), finalProjectId)).findFirst();
            voOptional.ifPresent(extObjVoList::remove);
        }

        // 更新 此项目信息
        // 封装 灰度扩展项目Vo
        GrayEnvExtObjVo extObjVo = new GrayEnvExtObjVo();
        extObjVo.setId(projectId);
        extObjVo.setGitName(gitProjectResponseVo.getName());
        extObjVo.setSubProjectPath(subProjectPath);
        extObjVo.setBranch(branchName);
        extObjVo.setCloneUrl(gitProjectResponseVo.getCloneUrl());
        extObjVoList.add(extObjVo);

        GrayEnvEntity updateEntity = new GrayEnvEntity();
        updateEntity.setId(id);
        updateEntity.setExtObj(JSON.toJSONString(extObjVoList));

        grayEnvMapper.updateByPrimaryKeySelective(updateEntity);
    }

    public void deleteProjectInGrayEnv(Integer id, String projectId) {
        GrayEnvEntity grayEnvEntity = grayEnvMapper.selectByPrimaryKey(id);
        if (Objects.isNull(grayEnvEntity) || !Objects.equals(grayEnvEntity.getState(), StateEnum.NORMAL.getValue())) {
            throw new ServiceException(FailureEnum.GRAY_ENV_NOT_EXIST);
        }
        List<GrayEnvExtObjVo> extObjVoList = new ArrayList<>();
        String extObj = grayEnvEntity.getExtObj();
        if (StringUtils.isNotBlank(extObj)) {
            extObjVoList = JSON.parseArray(extObj, GrayEnvExtObjVo.class);
            Optional<GrayEnvExtObjVo> voOptional = extObjVoList.stream().filter(loopVo -> Objects.equals(loopVo.getId(), projectId)).findFirst();
            voOptional.ifPresent(extObjVoList::remove);
        }

        // 更新 此项目信息
        GrayEnvEntity updateEntity = new GrayEnvEntity();
        updateEntity.setId(id);
        updateEntity.setExtObj(JSON.toJSONString(extObjVoList));

        grayEnvMapper.updateByPrimaryKeySelective(updateEntity);
    }

    public List<GrayEnvResponseVo> findAllGrayEnv() {
        GrayEnvEntityExample example = new GrayEnvEntityExample();
        example.createCriteria().andStateEqualTo(StateEnum.NORMAL.getValue());
        List<GrayEnvEntity> grayEnvEntityList = grayEnvMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(grayEnvEntityList)) {
            return Collections.emptyList();
        }

        List<GrayEnvResponseVo> returnList = new ArrayList<>();
        for (GrayEnvEntity loopEntity : grayEnvEntityList) {
            GrayEnvResponseVo envVo = new GrayEnvResponseVo();
            BeanUtils.copyProperties(loopEntity, envVo);
            String extObj = loopEntity.getExtObj();

            if (StringUtils.isNotBlank(extObj)) {
                envVo.setExtObjList(JSON.parseArray(extObj, GrayEnvExtObjVo.class));
            }
            returnList.add(envVo);
        }
        return returnList;
    }

    public void runProjectInGrayEnv(Integer id, String projectId) {
        // 获取灰度环境项目
        GrayEnvEntity grayEnvEntity = grayEnvMapper.selectByPrimaryKey(id);
        if (Objects.isNull(grayEnvEntity) || !Objects.equals(grayEnvEntity.getState(), StateEnum.NORMAL.getValue())) {
            throw new ServiceException(FailureEnum.GIT_FETCH_EXCEPTION);
        }

        List<GrayEnvExtObjVo> extObjVoList = null;
        String extObj = grayEnvEntity.getExtObj();
        if (StringUtils.isBlank(extObj)) {
            throw new ServiceException(FailureEnum.GRAY_ENV_PROJECT_NOT_EXIST);
        }
        extObjVoList = JSON.parseArray(extObj, GrayEnvExtObjVo.class);
        Optional<GrayEnvExtObjVo> extObjOptional = extObjVoList.stream().filter(loopVo -> Objects.equals(loopVo.getId(), projectId)).findFirst();
        if (!extObjOptional.isPresent()) {
            throw new ServiceException(FailureEnum.GRAY_ENV_PROJECT_NOT_EXIST);
        }
        GrayEnvExtObjVo extObjVo = extObjOptional.get();
        String gitName = extObjVo.getGitName();
        String subProjectPath = extObjVo.getSubProjectPath();
        String branch = extObjVo.getBranch();
        String cloneUrl = extObjVo.getCloneUrl();

        // 子项目 兼容处理
        String gitClonePath = k8sProjectClonePath + gitName;
        String executePath = gitClonePath + (StringUtils.isBlank(subProjectPath) ? "" : File.separator + subProjectPath);

        // 拉取代码
        FileUtil.del(gitClonePath);
        gitService.download(cloneUrl,branch,gitClonePath);

        // 文件复制 处理
        String startShPath = executePath + File.separator + "deploy.sh";

        // 构建镜像
        String version = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddHHmmss"));
        String commandLineStr = format("sh %s %s %s %s %s", startShPath, executePath, dockerRepositoryPwd, grayEnvEntity.getName(), version);
        String returnLogStr = CmdUtil.exec(10, TimeUnit.SECONDS, commandLineStr);
        log.info("runProjectInGrayEnv execute log \n : {}", returnLogStr);

        // 发布服务
        try {
            String deploymentFilePath = executePath + File.separator + "deployment.yaml";
            k8sService.createDeployment(grayEnvEntity.getName(),deploymentFilePath);
        } catch (Exception e) {
            log.error("k8s deployment error ,", e);
            throw new ServiceException(FailureEnum.K8S_DEPLOY_DEPLOYMENT);
        }
    }

    private void copyFileToExecutePath(String executePath, String fileName) {
        String filePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("k8s-gray/" + fileName)).getPath();
        copy(filePath, executePath + File.separator + fileName);
    }

    public void copy(String resourcePath, String targetPath) {
        try (FileInputStream fis = new FileInputStream(resourcePath); FileOutputStream fos = new FileOutputStream(targetPath)) {
            IOUtils.copy(fis, fos);
        } catch (Exception e) {
            throw new ServiceException(FailureEnum.FILE_COPY_EXCEPTION);
        }
    }
}
