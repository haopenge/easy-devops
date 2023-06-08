package com.easy.devops.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import com.easy.core.util.State;
import com.easy.core.util.StringPool;
import com.easy.devops.api.domain.bo.HostAndUsername;
import com.easy.devops.api.domain.entity.RepositoryEntity;
import com.easy.devops.api.domain.entity.RepositoryEntityExample;
import com.easy.devops.api.domain.enumx.AdminApiFailureEnum;
import com.easy.devops.api.domain.enumx.StateEnum;
import com.easy.devops.api.domain.vo.request.AddRepositoryRequestVo;
import com.easy.devops.api.domain.vo.request.EditRepositoryRequestVo;
import com.easy.devops.api.domain.vo.response.RepositoryResponseVo;
import com.easy.devops.api.exception.AdminApiException;
import com.easy.devops.api.mapper.RepositoryEntityMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RepositoryService {

    @Resource
    private RepositoryEntityMapper repositoryEntityMapper;

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

        if (!CollectionUtil.isNotEmpty(repositoryEntities)) {
            throw new AdminApiException(AdminApiFailureEnum.REPOSITORY_NAME_EXISTS);
        }

        RepositoryEntity addEntity = new RepositoryEntity();
        addEntity.setDescription(requestVo.getDescription());
        addEntity.setName(requestVo.getName());

        String cloneUrl = requestVo.getCloneUrl();
        addEntity.setCloneUrlType(cloneUrl.contains(StringPool.AT) ? State.YES.getId() :  State.NO.getId());

        addEntity.setCloneUrl(requestVo.getCloneUrl());
        addEntity.setEasyAuthenticateId(requestVo.getEasyAuthenticateId());
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
        updateEntity.setDescription(requestVo.getDescription());
        updateEntity.setCloneUrl(requestVo.getCloneUrl());
        updateEntity.setEasyAuthenticateId(requestVo.getEasyAuthenticateId());
        repositoryEntityMapper.insertSelective(updateEntity);
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
        return BeanUtil.copyToList(entityList, RepositoryResponseVo.class);
    }

}
