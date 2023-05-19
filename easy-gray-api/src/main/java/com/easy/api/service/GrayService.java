package com.easy.api.service;

import cn.hutool.core.io.FileUtil;
import com.easy.api.domain.entity.GrayEnvEntity;
import com.easy.api.domain.entity.GrayEnvEntityExample;
import com.easy.api.domain.entity.GrayProjectEntity;
import com.easy.api.domain.entity.GrayProjectEntityExample;
import com.easy.api.domain.enumx.FailureEnum;
import com.easy.api.domain.vo.request.AddProjectToGrayEnvRequestVo;
import com.easy.api.domain.vo.request.EditProjectRequestVo;
import com.easy.api.domain.vo.request.GrayAddRequestVo;
import com.easy.api.domain.vo.request.GrayEditRequestVo;
import com.easy.api.domain.vo.response.GitProjectResponseVo;
import com.easy.api.domain.vo.response.GrayEnvResponseVo;
import com.easy.api.exception.ServiceException;
import com.easy.api.mapper.GrayEnvEntityMapper;
import com.easy.api.mapper.GrayProjectEntityMapper;
import com.easy.api.util.ObjectUtil;
import com.easy.core.util.CmdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class GrayService {

    @Resource
    private GrayEnvEntityMapper grayEnvMapper;

    @Resource
    private GrayProjectEntityMapper grayProjectEntityMapper;

    @Resource
    private K8sService k8sService;

    @Resource
    private IGitService gitService;

    @Value("${docker.repository_username}")
    private String dockerRepositoryUsername;

    @Value("${docker.repository_pwd:}")
    private String dockerRepositoryPwd;

    @Value("${git.name:xiaoyuxxx}")
    private String gitUserName;

    /**
     * 项目克隆地址
     */
    public static final String PROJECT_CLONE_PATH = System.getProperty("user.dir") + File.separator + "git";


    public Integer addGrayEnv(GrayAddRequestVo requestVo) {
        GrayEnvEntity saveEntity = new GrayEnvEntity();
        saveEntity.setName(requestVo.getName());
        saveEntity.setDescription(requestVo.getDescription());
        saveEntity.setExpireTime(requestVo.getExpireTime());

        grayEnvMapper.insertSelective(saveEntity);

        try {
            k8sService.createNamespace(requestVo.getName());
        } catch (Exception e) {
            throw new ServiceException(FailureEnum.K8S_NAMESPACE_CREATE_ERROR);
        }

        return saveEntity.getId();
    }

    /**
     * 更新环境信息
     *
     * @param requestVo 请求Vo
     */
    public void editGrayEnv(GrayEditRequestVo requestVo) {
        GrayEnvEntity grayEnvEntity = grayEnvMapper.selectByPrimaryKey(requestVo.getId());
        if (Objects.isNull(grayEnvEntity)) {
            return;
        }
        GrayEnvEntity updateEntity = new GrayEnvEntity();
        updateEntity.setId(requestVo.getId());
        updateEntity.setExpireTime(requestVo.getExpireTime());
        updateEntity.setDescription(requestVo.getDescription());
        grayEnvMapper.updateByPrimaryKeySelective(updateEntity);
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
    public void addProjectToGrayEnv(AddProjectToGrayEnvRequestVo requestVo) {
        Integer envId = requestVo.getEnvId();
        GrayEnvEntity grayEnvEntity = grayEnvMapper.selectByPrimaryKey(envId);
        if (Objects.isNull(grayEnvEntity)) {
            throw new ServiceException(FailureEnum.GRAY_ENV_NOT_EXIST);
        }

        String fullName = requestVo.getFullName();
        GitProjectResponseVo gitProjectResponseVo = gitService.findRepositoryByFullName(gitUserName + "/" + "easy-gray");
        if (Objects.isNull(gitProjectResponseVo)) {
            throw new ServiceException(FailureEnum.GIT_FETCH_EXCEPTION);
        }

        int i = fullName.lastIndexOf("/");
        String name = (i == -1 ? fullName : fullName.substring(i + 1));

        GrayProjectEntityExample example = new GrayProjectEntityExample();
        example.createCriteria().andGrayEnvIdEqualTo(envId).andNameEqualTo(name);
        List<GrayProjectEntity> grayProjectEntities = grayProjectEntityMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(grayProjectEntities)) {
            throw new ServiceException(FailureEnum.GRAY_ENV_PROJECT_EXIST);
        }

        // 新增项目
        GrayProjectEntity projectEntity = new GrayProjectEntity();
        projectEntity.setName(name);
        projectEntity.setGitName(fullName.substring(0, fullName.indexOf("/")));
        projectEntity.setFullName(fullName);
        projectEntity.setBranch(requestVo.getBranchName());
        projectEntity.setCloneUrl(gitProjectResponseVo.getCloneUrl());
        projectEntity.setDescription(gitProjectResponseVo.getDescription());
        projectEntity.setGrayEnvId(envId);
        grayProjectEntityMapper.insertSelective(projectEntity);
    }

    /**
     * 编辑项目
     *
     * @param editProjectRequestVo 请求vo
     */
    public void editProject(EditProjectRequestVo editProjectRequestVo) {
        GrayProjectEntity projectEntity = grayProjectEntityMapper.selectByPrimaryKey(editProjectRequestVo.getId());
        if (Objects.isNull(projectEntity)) {
            throw new ServiceException(FailureEnum.GRAY_ENV_PROJECT_NOT_EXIST);
        }
        // 更新项目
        GrayProjectEntity updateEntity = new GrayProjectEntity();
        updateEntity.setId(editProjectRequestVo.getId());
        updateEntity.setBranch(editProjectRequestVo.getBranchName());
        grayProjectEntityMapper.updateByPrimaryKeySelective(updateEntity);
    }

    public void deleteProjectInGrayEnv(Integer projectId) {
        GrayProjectEntity projectEntity = grayProjectEntityMapper.selectByPrimaryKey(projectId);
        if (Objects.isNull(projectEntity)) {
            throw new ServiceException(FailureEnum.GRAY_ENV_PROJECT_NOT_EXIST);
        }
        grayProjectEntityMapper.deleteByPrimaryKey(projectId);
    }

    public List<GrayEnvResponseVo> findAllGrayEnv() {
        GrayEnvEntityExample example = new GrayEnvEntityExample();
        List<GrayEnvEntity> grayEnvEntityList = grayEnvMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(grayEnvEntityList)) {
            return Collections.emptyList();
        }

        List<GrayEnvResponseVo> returnList = new ArrayList<>();
        for (GrayEnvEntity loopEntity : grayEnvEntityList) {
            GrayEnvResponseVo envVo = new GrayEnvResponseVo();
            BeanUtils.copyProperties(loopEntity, envVo);
            returnList.add(envVo);
        }
        return returnList;
    }

    public void runProjectInGrayEnv(Integer projectId) {
        GrayProjectEntity projectEntity = grayProjectEntityMapper.selectByPrimaryKey(projectId);
        if (Objects.isNull(projectEntity)) {
            throw new ServiceException(FailureEnum.GRAY_ENV_PROJECT_NOT_EXIST);
        }
        GrayEnvEntity grayEnvEntity = grayEnvMapper.selectByPrimaryKey(projectEntity.getGrayEnvId());
        if (Objects.isNull(grayEnvEntity)) {
            throw new ServiceException(FailureEnum.GRAY_ENV_NOT_EXIST);
        }

        String fullName = projectEntity.getFullName();
        String gitName = projectEntity.getGitName();
        String branch = projectEntity.getBranch();
        String cloneUrl = projectEntity.getCloneUrl();

        // 子项目 兼容处理
        String gitClonePath = PROJECT_CLONE_PATH + File.separator + gitName;
        String executePath = PROJECT_CLONE_PATH + File.separator + fullName;

        log.info("runProjectInGrayEnv project download start , path = " + PROJECT_CLONE_PATH);

        // 拉取代码
        FileUtil.del(gitClonePath);
        gitService.download(cloneUrl, branch, gitClonePath);

        log.info("runProjectInGrayEnv project download ok , path = " + PROJECT_CLONE_PATH);

        // 文件复制 处理
        String startShPath = executePath + File.separator + "build.sh";

        // 执行运行服务脚本
        String version = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddHHmmss"));
        String grayEnvName = grayEnvEntity.getName();
        CmdUtil.exec("sh",startShPath, dockerRepositoryUsername, dockerRepositoryPwd, grayEnvName, version);
    }

    public void copy(String resourcePath, String targetPath) {
        try (FileInputStream fis = new FileInputStream(resourcePath); FileOutputStream fos = new FileOutputStream(targetPath)) {
            IOUtils.copy(fis, fos);
        } catch (Exception e) {
            throw new ServiceException(FailureEnum.FILE_COPY_EXCEPTION);
        }
    }

    /**
     * 获取环境中的项目
     *
     * @param envId 环境id
     * @return 项目
     */
    public List<GitProjectResponseVo> findProject(Integer envId) {
        GrayProjectEntityExample example = new GrayProjectEntityExample();
        example.createCriteria().andGrayEnvIdEqualTo(envId);
        List<GrayProjectEntity> grayProjectEntities = grayProjectEntityMapper.selectByExample(example);
        return ObjectUtil.convertList(grayProjectEntities, GitProjectResponseVo.class);
    }
}
