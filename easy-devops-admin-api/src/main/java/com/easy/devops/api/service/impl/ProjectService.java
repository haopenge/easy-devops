package com.easy.devops.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.easy.devops.api.domain.entity.*;
import com.easy.devops.api.domain.enumx.AdminApiFailureEnum;
import com.easy.devops.api.domain.enumx.GitRepositoryTypeEnum;
import com.easy.devops.api.domain.vo.request.AddProjectRequestVo;
import com.easy.devops.api.domain.vo.request.EditProjectRequestVo;
import com.easy.devops.api.domain.vo.response.ProjectResponseVo;
import com.easy.devops.api.exception.AdminApiException;
import com.easy.devops.api.mapper.CertificateEntityMapper;
import com.easy.devops.api.mapper.EnvEntityMapper;
import com.easy.devops.api.mapper.ProjectEntityMapper;
import com.easy.devops.api.mapper.RepositoryEntityMapper;
import com.easy.devops.api.service.IGitService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


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

    @Resource
    private CertificateEntityMapper certificateEntityMapper;

    public Integer addProject(AddProjectRequestVo requestVo) {
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

        // 校验此项目是否已存在
        ProjectEntityExample example = new ProjectEntityExample();
        example.createCriteria().andNameEqualTo(requestVo.getName()).andEasyEnvIdNotEqualTo(envId);
        List<ProjectEntity> entityList = projectEntityMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(entityList)) {
            throw new AdminApiException(AdminApiFailureEnum.ENV_PROJECT_EXIST);
        }

        // 校验仓库
        Integer repositoryId = requestVo.getEasyRepositoryId();
        RepositoryEntity repositoryEntity = repositoryEntityMapper.selectByPrimaryKey(repositoryId);
        if (Objects.isNull(repositoryEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.REPOSITORY_NOT_EXISTS);
        }

        // 新增项目
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(requestVo.getName());
        projectEntity.setEasyEnvId(envId);
        projectEntity.setEasyRepositoryId(repositoryId);
        projectEntity.setBranch(requestVo.getBranch());
        projectEntityMapper.insertSelective(projectEntity);

        return projectEntity.getId();
    }

    public void editProject(EditProjectRequestVo requestVo) {
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

        ProjectEntity projectEntity = projectEntityMapper.selectByPrimaryKey(requestVo.getId());
        if (Objects.isNull(projectEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.ENV_PROJECT_NOT_EXIST);
        }

        // 校验仓库
        Integer repositoryId = requestVo.getEasyRepositoryId();
        RepositoryEntity repositoryEntity = repositoryEntityMapper.selectByPrimaryKey(repositoryId);
        if (Objects.isNull(repositoryEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.REPOSITORY_NOT_EXISTS);
        }

        // 新增项目
        ProjectEntity updateEntity = new ProjectEntity();
        updateEntity.setId(requestVo.getId());
        updateEntity.setEasyEnvId(envId);
        updateEntity.setEasyRepositoryId(repositoryId);
        updateEntity.setBranch(requestVo.getBranch());
        projectEntityMapper.updateByPrimaryKeySelective(updateEntity);
    }

    public void deleteById(Integer id) {
        ProjectEntity projectEntity = projectEntityMapper.selectByPrimaryKey(id);
        if (Objects.isNull(projectEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.ENV_PROJECT_NOT_EXIST);
        }
        // TODO 校验服务是否已在运行
        projectEntityMapper.deleteByPrimaryKey(id);
    }

    public List<String> findBranches(Integer easyRepositoryId) {
        RepositoryEntity repositoryEntity = repositoryEntityMapper.selectByPrimaryKey(easyRepositoryId);
        if (Objects.isNull(repositoryEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.REPOSITORY_NOT_EXISTS);
        }
        CertificateEntity certificateEntity = certificateEntityMapper.selectByPrimaryKey(repositoryEntity.getEasyCertificateId());
        if (Objects.isNull(certificateEntity) || !GitRepositoryTypeEnum.existEnum(certificateEntity.getRepositoryType())) {
            throw new AdminApiException(AdminApiFailureEnum.CERTIFICATE_NOT_EXISTS);
        }
        IGitService gitService = GitRepositoryTypeEnum.getGitServiceByValue(certificateEntity.getRepositoryType());
        return gitService.getRemoteBranches(certificateEntity.getUsername(), repositoryEntity.getName(), certificateEntity.getAccessToken());
    }

    public List<ProjectResponseVo> findAll(Integer envId) {
        if(envId != 0){
            EnvEntity envEntity = envEntityMapper.selectByPrimaryKey(envId);
            if (Objects.isNull(envEntity)) {
                throw new AdminApiException(AdminApiFailureEnum.GRAY_ENV_NOT_EXIST);
            }
        }
        List<ProjectEntity> projectEntities = projectEntityMapper.selectByExample(new ProjectEntityExample());
        if(CollectionUtils.isEmpty(projectEntities)){
            return Collections.emptyList();
        }

        List<CertificateEntity> certificateEntities = certificateEntityMapper.selectByExample(new CertificateEntityExample());
        Map<Integer, CertificateEntity> certificateMap = certificateEntities.stream().collect(Collectors.toMap(CertificateEntity::getId, Function.identity()));

        RepositoryEntityExample example = new RepositoryEntityExample();
        List<RepositoryEntity> repositoryEntityList = repositoryEntityMapper.selectByExample(example);
        example.createCriteria().andIdIn(projectEntities.stream().map(ProjectEntity::getEasyRepositoryId).collect(Collectors.toList()));
        repositoryEntityMapper.selectByExample(example);
        Map<Integer, RepositoryEntity> repositoryMap = repositoryEntityList.stream().collect(Collectors.toMap(RepositoryEntity::getId, Function.identity()));

        List<ProjectResponseVo> dataList = new ArrayList<>();
        for (ProjectEntity loopProject : projectEntities) {
            ProjectResponseVo responseVo = BeanUtil.toBean(loopProject, ProjectResponseVo.class);

            Integer easyRepositoryId = loopProject.getEasyRepositoryId();
            RepositoryEntity repositoryEntity = repositoryMap.get(easyRepositoryId);
            if(Objects.nonNull(repositoryEntity)){
                responseVo.setEasyRepositoryName(repositoryEntity.getName());

                CertificateEntity certificateEntity = certificateMap.get(repositoryEntity.getEasyCertificateId());
                if(Objects.nonNull(certificateEntity)){
                    responseVo.setEasyCertificateName(certificateEntity.getName());
                }
            }
            dataList.add(responseVo);
        }
        return dataList;
    }
}
