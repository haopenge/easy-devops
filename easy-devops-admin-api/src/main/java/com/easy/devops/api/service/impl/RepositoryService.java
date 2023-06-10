package com.easy.devops.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.easy.devops.api.domain.entity.CertificateEntity;
import com.easy.devops.api.domain.entity.CertificateEntityExample;
import com.easy.devops.api.domain.entity.RepositoryEntity;
import com.easy.devops.api.domain.entity.RepositoryEntityExample;
import com.easy.devops.api.domain.enumx.AdminApiFailureEnum;
import com.easy.devops.api.domain.enumx.GitRepositoryTypeEnum;
import com.easy.devops.api.domain.vo.request.AddRepositoryRequestVo;
import com.easy.devops.api.domain.vo.request.EditRepositoryRequestVo;
import com.easy.devops.api.domain.vo.response.GitCertificateResponseVo;
import com.easy.devops.api.domain.vo.response.RepositoryResponseVo;
import com.easy.devops.api.exception.AdminApiException;
import com.easy.devops.api.mapper.CertificateEntityMapper;
import com.easy.devops.api.mapper.RepositoryEntityMapper;
import com.easy.devops.api.service.IGitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RepositoryService {

    @Resource
    private RepositoryEntityMapper repositoryEntityMapper;

    @Resource
    private GiteeService giteeService;

    @Resource
    private GithubService githubService;

    @Resource
    private GitlabService gitlabService;

    @Resource
    private CertificateEntityMapper certificateEntityMapper;

    public List<GitCertificateResponseVo> findGitRepositories(Integer certificateId) {
        CertificateEntity certificateEntity = certificateEntityMapper.selectByPrimaryKey(certificateId);
        if(Objects.isNull(certificateEntity) || !GitRepositoryTypeEnum.existEnum(certificateEntity.getRepositoryType())){
            throw new AdminApiException(AdminApiFailureEnum.CERTIFICATE_NOT_EXISTS);
        }

        GitRepositoryTypeEnum gitRepositoryTypeEnum = GitRepositoryTypeEnum.valueOf(certificateEntity.getRepositoryType());
        IGitService gitService = null;
        switch (gitRepositoryTypeEnum) {
            case GITHUB:
                gitService = githubService;
                break;
            case GITEE:
                gitService = giteeService;
                break;
            case GITLAB:
            default:
                gitService = gitlabService;
                break;
        }
        assert gitService != null;
        return gitService.findRepositories(certificateEntity.getAccessToken());
    }

    /**
     * 新增仓库
     *
     * @param requestVo 请求vo
     * @return 新增仓库成功后db id
     */
    public Integer add(AddRepositoryRequestVo requestVo) {
        // 仓库名称判重
        RepositoryEntityExample example = new RepositoryEntityExample();
        example.createCriteria().andNameEqualTo(requestVo.getName());
        List<RepositoryEntity> repositoryEntities = repositoryEntityMapper.selectByExample(example);

        if (CollectionUtil.isNotEmpty(repositoryEntities)) {
            throw new AdminApiException(AdminApiFailureEnum.REPOSITORY_NAME_EXISTS);
        }

        RepositoryEntity addEntity = new RepositoryEntity();
        addEntity.setDescription(requestVo.getDescription());
        addEntity.setName(requestVo.getName());
        addEntity.setCloneUrl(requestVo.getCloneUrl());
        addEntity.setEasyCertificateId(requestVo.getEasyCertificateId());
        addEntity.setBranch(requestVo.getBranch());
        repositoryEntityMapper.insertSelective(addEntity);

        return addEntity.getId();
    }

    /**
     * 编辑仓库
     *
     * @param requestVo 请求vo
     */
    public void edit(EditRepositoryRequestVo requestVo) {
        RepositoryEntity dbEntity = repositoryEntityMapper.selectByPrimaryKey(requestVo.getId());
        if (Objects.isNull(dbEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.REPOSITORY_NOT_EXISTS);
        }

        RepositoryEntity updateEntity = new RepositoryEntity();
        updateEntity.setId(requestVo.getId());
        updateEntity.setName(requestVo.getName());
        updateEntity.setDescription(requestVo.getDescription());
        updateEntity.setCloneUrl(requestVo.getCloneUrl());
        updateEntity.setEasyCertificateId(requestVo.getEasyCertificateId());
        updateEntity.setBranch(requestVo.getBranch());
        repositoryEntityMapper.updateByPrimaryKeySelective(updateEntity);
    }

    /**
     * 删除仓库
     *
     * @param id id
     */
    public void deleteById(Integer id) {
        RepositoryEntity dbEntity = repositoryEntityMapper.selectByPrimaryKey(id);
        if (Objects.isNull(dbEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.REPOSITORY_NOT_EXISTS);
        }
        repositoryEntityMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取仓库信息
     *
     * @return 仓库信息vo
     */
    public List<RepositoryResponseVo> findAll() {
        List<RepositoryEntity> entityList = repositoryEntityMapper.selectByExample(new RepositoryEntityExample());
        List<RepositoryResponseVo> dataList = BeanUtil.copyToList(entityList, RepositoryResponseVo.class);
        if(CollectionUtil.isEmpty(dataList)){
            return Collections.emptyList();
        }
        List<CertificateEntity> certificateEntities = certificateEntityMapper.selectByExample(new CertificateEntityExample());
        Map<Integer, CertificateEntity> certificateMap = certificateEntities.stream().collect(Collectors.toMap(CertificateEntity::getId, Function.identity()));

        for (RepositoryResponseVo responseVo : dataList) {
            Integer easyCertificateId = responseVo.getEasyCertificateId();
            CertificateEntity certificateEntity = certificateMap.get(easyCertificateId);
            if(Objects.isNull(certificateEntity)){
                continue;
            }
            responseVo.setEasyCertificateName(certificateEntity.getName());
        }
        return dataList;
    }

}
