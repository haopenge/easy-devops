package com.easy.devops.api.service.impl;

import com.easy.devops.api.config.properties.BuildProperties;
import com.easy.devops.api.config.properties.DockerProperties;
import com.easy.devops.api.config.properties.GitProperties;
import com.easy.devops.api.domain.enumx.AdminApiFailureEnum;
import com.easy.devops.api.domain.vo.request.AddProjectRequestVo;
import com.easy.devops.api.domain.vo.request.EditProjectRequestVo;
import com.easy.devops.api.domain.vo.request.GrayAddRequestVo;
import com.easy.devops.api.domain.vo.request.GrayEditRequestVo;
import com.easy.devops.api.domain.vo.response.GitProjectResponseVo;
import com.easy.devops.api.domain.vo.response.GrayEnvResponseVo;
import com.easy.devops.api.exception.AdminApiException;
import com.easy.devops.api.mapper.EnvEntityMapper;
import com.easy.devops.api.mapper.ProjectEntityMapper;
import com.easy.devops.api.service.IGitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

@Slf4j
@Service
public class GrayService {

    @Resource
    private EnvEntityMapper easyEnvEntityMapper;

    @Resource
    private ProjectEntityMapper easyProjectEntityMapper;

    @Resource
    private K8sService k8sService;

    @Resource
    private IGitService gitService;

    @Resource
    private DockerProperties dockerProperties;

    @Resource
    private GitProperties gitProperties;

    @Resource
    private BuildProperties buildProperties;

    public Integer addGrayEnv(GrayAddRequestVo requestVo) {
        /*EasyEnvEntity saveEntity = new EasyEnvEntity();
        saveEntity.setName(requestVo.getName());
        saveEntity.setDescription(requestVo.getDescription());
        saveEntity.setExpireTime(requestVo.getExpireTime());

        easyEnvEntityMapper.insertSelective(saveEntity);

        try {
            k8sService.createNamespace(requestVo.getName());
        } catch (Exception e) {
            throw new AdminApiException(AdminApiFailureEnum.K8S_NAMESPACE_CREATE_ERROR);
        }

        return saveEntity.getId();*/

        return 0;
    }

    /**
     * 更新环境信息
     *
     * @param requestVo 请求Vo
     */
    public void editGrayEnv(GrayEditRequestVo requestVo) {
       /* EasyEnvEntity grayEnvEntity = easyEnvEntityMapper.selectByPrimaryKey(requestVo.getId());
        if (Objects.isNull(grayEnvEntity)) {
            return;
        }
        EasyEnvEntity updateEntity = new EasyEnvEntity();
        updateEntity.setId(requestVo.getId());
        updateEntity.setExpireTime(requestVo.getExpireTime());
        updateEntity.setDescription(requestVo.getDescription());
        easyEnvEntityMapper.updateByPrimaryKeySelective(updateEntity);*/
    }

    public void deleteById(Integer id) {
       /* EasyEnvEntity grayEnvEntity = easyEnvEntityMapper.selectByPrimaryKey(id);
        if (Objects.isNull(grayEnvEntity)) {
            return;
        }
        easyEnvEntityMapper.deleteByPrimaryKey(id);
        try {
            k8sService.createNamespace(grayEnvEntity.getName());
        } catch (Exception e) {
            throw new AdminApiException(AdminApiFailureEnum.K8S_NAMESPACE_CREATE_ERROR);
        }*/
    }

    /**
     * 添加项目到灰度环境
     */
    public void addProjectToGrayEnv(AddProjectRequestVo requestVo) {

    }

    /**
     * 编辑项目
     *
     * @param editProjectRequestVo 请求vo
     */
    public void editProject(EditProjectRequestVo editProjectRequestVo) {
       /* EasyProjectEntity projectEntity = easyProjectEntityMapper.selectByPrimaryKey(editProjectRequestVo.getId());
        if (Objects.isNull(projectEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.GRAY_ENV_PROJECT_NOT_EXIST);
        }
        // 更新项目
        EasyProjectEntity updateEntity = new EasyProjectEntity();
        updateEntity.setId(editProjectRequestVo.getId());
        updateEntity.setBranch(editProjectRequestVo.getBranchName());
        easyProjectEntityMapper.updateByPrimaryKeySelective(updateEntity);*/
    }

    public void deleteProjectInGrayEnv(Integer projectId) {
        /*EasyProjectEntity projectEntity = easyProjectEntityMapper.selectByPrimaryKey(projectId);
        if (Objects.isNull(projectEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.GRAY_ENV_PROJECT_NOT_EXIST);
        }
        easyProjectEntityMapper.deleteByPrimaryKey(projectId);*/
    }

    public List<GrayEnvResponseVo> findAllGrayEnv() {
       /* EasyEnvEntityExample example = new EasyEnvEntityExample();
        List<EasyEnvEntity> grayEnvEntityList = easyEnvEntityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(grayEnvEntityList)) {
            return Collections.emptyList();
        }

        List<GrayEnvResponseVo> returnList = new ArrayList<>();
        for (EasyEnvEntity loopEntity : grayEnvEntityList) {
            GrayEnvResponseVo envVo = new GrayEnvResponseVo();
            BeanUtils.copyProperties(loopEntity, envVo);
            returnList.add(envVo);
        }
        return returnList;*/
        return null;
    }

    public void runProjectInGrayEnv(Integer projectId) {
      /*  EasyProjectEntity projectEntity = easyProjectEntityMapper.selectByPrimaryKey(projectId);
        if (Objects.isNull(projectEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.GRAY_ENV_PROJECT_NOT_EXIST);
        }
        EasyEnvEntity grayEnvEntity = easyEnvEntityMapper.selectByPrimaryKey(projectEntity.getEasyEnvId());
        if (Objects.isNull(grayEnvEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.GRAY_ENV_NOT_EXIST);
        }

        String branch = projectEntity.getBranch();

        // 子项目 兼容处理
        String clonePath = buildProperties.getClonePath();

        String gitClonePath = clonePath + File.separator + grayEnvEntity.getName() + File.separator ; // TODO + gitName;
        String executePath = clonePath + File.separator + grayEnvEntity.getName() + File.separator ; // TODO fullName;

        log.info("runProjectInGrayEnv project download start , path = " + clonePath);

        // 拉取代码
        FileUtil.del(gitClonePath);
       // gitService.download(cloneUrl, branch, gitClonePath);

        log.info("runProjectInGrayEnv project download ok , path = " + clonePath);

        // 文件复制 处理
        String startShPath = executePath + File.separator + "build.sh";

        // 执行运行服务脚本
        String version = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddHHmmss"));
        String grayEnvName = grayEnvEntity.getName();
        CmdUtil.exec("sh", startShPath, dockerProperties.getUsername(), dockerProperties.getPassword(), grayEnvName, version);*/
    }

    /**
     * 停止运行
     *
     * @param id 项目id
     */
    public void stopProjectInGrayEnv(Integer id) {
        /*EasyProjectEntity projectEntity = easyProjectEntityMapper.selectByPrimaryKey(id);
        if (Objects.isNull(projectEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.GRAY_ENV_PROJECT_NOT_EXIST);
        }
        EasyEnvEntity grayEnvEntity = easyEnvEntityMapper.selectByPrimaryKey(projectEntity.getEasyEnvId());
        if (Objects.isNull(grayEnvEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.GRAY_ENV_NOT_EXIST);
        }
        String fullName = projectEntity.getBuildPath();
        String executePath = buildProperties.getClonePath() + File.separator + grayEnvEntity.getName() + File.separator + fullName;

        String deploymentYamlPath = executePath + File.separator + "deployment.yaml";
        CmdUtil.exec("kubectl", "delete", "-f", deploymentYamlPath);*/
    }

    public void copy(String resourcePath, String targetPath) {
        try (FileInputStream fis = new FileInputStream(resourcePath); FileOutputStream fos = new FileOutputStream(targetPath)) {
            IOUtils.copy(fis, fos);
        } catch (Exception e) {
            throw new AdminApiException(AdminApiFailureEnum.FILE_COPY_EXCEPTION);
        }
    }

    /**
     * 获取环境中的项目
     *
     * @param envId 环境id
     * @return 项目
     */
    public List<GitProjectResponseVo> findProject(Integer envId) {
        /*EasyProjectEntityExample example = new EasyProjectEntityExample();
        example.createCriteria().andEasyEnvIdEqualTo(envId);
        List<EasyProjectEntity> grayProjectEntities = easyProjectEntityMapper.selectByExample(example);
        return ObjectUtil.convertList(grayProjectEntities, GitProjectResponseVo.class);*/
        return null;
    }
}
