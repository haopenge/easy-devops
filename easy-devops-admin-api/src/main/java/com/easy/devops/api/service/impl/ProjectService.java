package com.easy.devops.api.service.impl;

import com.easy.devops.api.domain.entity.*;
import com.easy.devops.api.domain.enumx.AdminApiFailureEnum;
import com.easy.devops.api.domain.vo.request.AddProjectRequestVo;
import com.easy.devops.api.domain.vo.request.EditProjectRequestVo;
import com.easy.devops.api.exception.AdminApiException;
import com.easy.devops.api.mapper.EnvEntityMapper;
import com.easy.devops.api.mapper.ProjectEntityMapper;
import com.easy.devops.api.mapper.RepositoryEntityMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;


/**
 * 项目
 */
@Service
public class ProjectService {

    @Resource
    private ProjectEntityMapper projectEntityMapper;

    @Resource
    private EnvEntityMapper envEntityMapper;

    @Resource
    private RepositoryEntityMapper repositoryEntityMapper;

    public Integer addProject(AddProjectRequestVo requestVo) {
        // 校验此项目是否已存在
        ProjectEntityExample example = new ProjectEntityExample();
        example.createCriteria().andNameEqualTo(requestVo.getName()).andEasyEnvIdNotEqualTo(requestVo.getEnvId());
        List<ProjectEntity> entityList = projectEntityMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(entityList)) {
            throw new AdminApiException(AdminApiFailureEnum.ENV_PROJECT_EXIST);
        }

        // 校验环境
        Integer envId = requestVo.getEnvId();
        if (Objects.nonNull(envId)) {
            EnvEntity envEntity = envEntityMapper.selectByPrimaryKey(envId);
            if (Objects.isNull(envEntity)) {
                throw new AdminApiException(AdminApiFailureEnum.GRAY_ENV_NOT_EXIST);
            }
        } else {
            envId = 0;
        }

        // 校验仓库
        Integer repositoryId = requestVo.getRepositoryId();
        RepositoryEntity repositoryEntity = repositoryEntityMapper.selectByPrimaryKey(repositoryId);
        if (Objects.isNull(repositoryEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.REPOSITORY_NOT_EXISTS);
        }

        // 新增项目
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(requestVo.getName());
        projectEntity.setEasyEnvId(envId);
        projectEntity.setEasyRepositoryId(repositoryId);
        projectEntity.setBranch(requestVo.getBranchName());
        projectEntityMapper.insertSelective(projectEntity);

        return projectEntity.getId();
    }

    public void editProject(EditProjectRequestVo requestVo) {
        ProjectEntity projectEntity = projectEntityMapper.selectByPrimaryKey(requestVo.getId());
        if (Objects.isNull(projectEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.ENV_PROJECT_NOT_EXIST);
        }
        // 校验环境
        Integer envId = requestVo.getEnvId();
        if (Objects.nonNull(envId)) {
            EnvEntity envEntity = envEntityMapper.selectByPrimaryKey(envId);
            if (Objects.isNull(envEntity)) {
                throw new AdminApiException(AdminApiFailureEnum.GRAY_ENV_NOT_EXIST);
            }
        } else {
            envId = 0;
        }

        // 校验仓库
        Integer repositoryId = requestVo.getRepositoryId();
        RepositoryEntity repositoryEntity = repositoryEntityMapper.selectByPrimaryKey(repositoryId);
        if (Objects.isNull(repositoryEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.REPOSITORY_NOT_EXISTS);
        }

        // 新增项目
        ProjectEntity updateEntity = new ProjectEntity();
        updateEntity.setId(requestVo.getId());
        updateEntity.setEasyEnvId(envId);
        updateEntity.setEasyRepositoryId(repositoryId);
        updateEntity.setBranch(requestVo.getBranchName());
        projectEntityMapper.insertSelective(updateEntity);
    }

    public void deleteById(Integer id) {
        ProjectEntity projectEntity = projectEntityMapper.selectByPrimaryKey(id);
        if (Objects.isNull(projectEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.ENV_PROJECT_NOT_EXIST);
        }
        // TODO 校验服务是否已在运行
        projectEntityMapper.deleteByPrimaryKey(id);
    }
}
