package com.youlai.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.system.common.enums.AdminApiFailureEnum;
import com.youlai.system.common.enums.GitRepositoryTypeEnum;
import com.youlai.system.common.exception.AdminApiException;
import com.youlai.system.mapper.EasyCertificateMapper;
import com.youlai.system.mapper.EasyRepositoryMapper;
import com.youlai.system.model.entity.EasyCertificate;
import com.youlai.system.model.entity.EasyRepository;
import com.youlai.system.model.vo.request.AddRepositoryRequestVo;
import com.youlai.system.model.vo.request.EditRepositoryRequestVo;
import com.youlai.system.model.vo.response.GitCertificateResponseVo;
import com.youlai.system.model.vo.response.RepositoryResponseVo;
import com.youlai.system.service.EasyRepositoryService;
import com.youlai.system.service.IGitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author liupenghao
 * @description 针对表【easy_repository(仓库)】的数据库操作Service实现
 * @createDate 2023-06-24 16:47:41
 */
@Service
public class EasyRepositoryServiceImpl extends ServiceImpl<EasyRepositoryMapper, EasyRepository>
        implements EasyRepositoryService {

    @Resource
    private EasyRepositoryMapper repositoryMapper;

    @Resource
    private EasyCertificateMapper certificateMapper;

    public List<GitCertificateResponseVo> findGitRepositories(Integer certificateId) {
        EasyCertificate certificateEntity = certificateMapper.selectById(certificateId);
        if (Objects.isNull(certificateEntity) || !GitRepositoryTypeEnum.existEnum(certificateEntity.getRepositoryType())) {
            throw new AdminApiException(AdminApiFailureEnum.CERTIFICATE_NOT_EXISTS);
        }

        IGitService gitService = GitRepositoryTypeEnum.getGitServiceByValue(certificateEntity.getRepositoryType());
        return gitService.findRepositories(certificateEntity.getAccessToken());
    }

    /**
     * 新增仓库
     *
     * @param requestVo 请求vo
     * @return 新增仓库成功后db id
     */
    public Integer add(AddRepositoryRequestVo requestVo) {
        // 判断凭证是否存在
        EasyCertificate certificateEntity = certificateMapper.selectById(requestVo.getEasyCertificateId());
        if (certificateEntity == null) {
            throw new AdminApiException(AdminApiFailureEnum.CERTIFICATE_NOT_EXISTS);
        }

        // 判断git仓库是否存在
        IGitService gitService = GitRepositoryTypeEnum.getGitServiceByValue(certificateEntity.getRepositoryType());
        GitCertificateResponseVo gitRepository = gitService.findRepository(certificateEntity.getUsername(), requestVo.getName(), certificateEntity.getAccessToken());


        // 仓库名称判重
        EasyRepository repositoryEntity = repositoryMapper
                .selectOne(new LambdaQueryWrapper<EasyRepository>()
                        .eq(EasyRepository::getName, requestVo.getName())
                        .eq(EasyRepository::getEasyCertificateId, requestVo.getEasyCertificateId())
                );

        if (Objects.nonNull(repositoryEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.REPOSITORY_NAME_EXISTS);
        }

        EasyRepository addEntity = new EasyRepository();
        addEntity.setDescription(gitRepository.getDescription());
        addEntity.setName(gitRepository.getName());
        addEntity.setCloneUrl(gitRepository.getHttpCloneUrl());
        addEntity.setEasyCertificateId(requestVo.getEasyCertificateId());
        addEntity.setBranch(gitRepository.getDefaultBranch());
        repositoryMapper.insert(addEntity);

        return addEntity.getId();
    }

    /**
     * 编辑仓库
     *
     * @param requestVo 请求vo
     */
    public void edit(EditRepositoryRequestVo requestVo) {
        EasyRepository dbEntity = repositoryMapper.selectById(requestVo.getId());
        if (Objects.isNull(dbEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.REPOSITORY_NOT_EXISTS);
        }

        EasyRepository updateEntity = new EasyRepository();
        updateEntity.setId(requestVo.getId());
        updateEntity.setName(requestVo.getName());
        updateEntity.setDescription(requestVo.getDescription());
        updateEntity.setCloneUrl(requestVo.getCloneUrl());
        updateEntity.setEasyCertificateId(requestVo.getEasyCertificateId());
        updateEntity.setBranch(requestVo.getBranch());
        repositoryMapper.updateById(updateEntity);
    }

    /**
     * 删除仓库
     *
     * @param ids ids
     */
    public void deleteById(List<Integer> ids) {
        removeByIds(ids);
    }

    /**
     * 获取仓库信息
     *
     * @return 仓库信息vo
     */
    public List<RepositoryResponseVo> findAll() {
        List<EasyRepository> entityList = repositoryMapper.selectList(new LambdaQueryWrapper<>());
        List<RepositoryResponseVo> dataList = BeanUtil.copyToList(entityList, RepositoryResponseVo.class);
        if (CollUtil.isEmpty(dataList)) {
            return Collections.emptyList();
        }
        List<EasyCertificate> certificateEntities = certificateMapper.selectList(new LambdaQueryWrapper<>());
        Map<Integer, EasyCertificate> certificateMap = certificateEntities.stream().collect(Collectors.toMap(EasyCertificate::getId, Function.identity()));

        for (RepositoryResponseVo responseVo : dataList) {
            Integer easyCertificateId = responseVo.getEasyCertificateId();
            EasyCertificate certificateEntity = certificateMap.get(easyCertificateId);
            if (Objects.isNull(certificateEntity)) {
                continue;
            }
            responseVo.setEasyCertificateName(certificateEntity.getName());
        }
        return dataList;
    }

}




