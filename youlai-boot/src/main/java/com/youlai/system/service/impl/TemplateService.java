package com.youlai.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.system.common.enums.AdminApiFailureEnum;
import com.youlai.system.common.exception.AdminApiException;
import com.youlai.system.config.properties.GlobalProperties;
import com.youlai.system.mapper.EasyTemplateMapper;
import com.youlai.system.model.entity.EasyTemplate;
import com.youlai.system.model.vo.request.AddTemplateRequestVo;
import com.youlai.system.model.vo.request.EditTemplateRequestVo;
import com.youlai.system.model.vo.response.TemplateResponseVo;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class TemplateService {

    @Resource
    private EasyTemplateMapper templateEntityMapper;

    @Resource
    private GlobalProperties globalProperties;

    public Integer add(AddTemplateRequestVo requestVo) {
        EasyTemplate template = templateEntityMapper.selectOne(new LambdaQueryWrapper<EasyTemplate>()
                .eq(EasyTemplate::getName, requestVo.getName())
                .eq(EasyTemplate::getType, requestVo.getType())
        );
        if (Objects.nonNull(template)) {
            throw new AdminApiException(AdminApiFailureEnum.TEMPLATE_NAME_EXIST);
        }

        String contentFileKey = UUID.fastUUID().toString();
        String contentFilePath = globalProperties.getDeployTemplateFileBasePath() + File.separator + contentFileKey;
        FileUtil.mkdir(FileUtil.getParent(contentFilePath, 1));
        try (
                InputStream is = new ByteArrayInputStream(requestVo.getContent().getBytes(StandardCharsets.UTF_8));
                OutputStream os = Files.newOutputStream(Paths.get(contentFilePath))
        ) {
            IoUtil.copy(is, os);
        } catch (Exception e) {
            throw new AdminApiException(AdminApiFailureEnum.TEMPLATE_NAME_EXIST);
        }

        EasyTemplate addEntity = new EasyTemplate();
        addEntity.setName(requestVo.getName());
        addEntity.setType(requestVo.getType());
        addEntity.setContentFileKey(contentFileKey);
        templateEntityMapper.insert(addEntity);
        return addEntity.getId();
    }

    public List<TemplateResponseVo> findAll() {
        List<EasyTemplate> templateEntities = templateEntityMapper.selectList(new LambdaQueryWrapper<>());
        if (CollectionUtils.isEmpty(templateEntities)) {
            return Collections.emptyList();
        }
        List<TemplateResponseVo> dataList = new ArrayList<>();
        for (EasyTemplate loopTemplate : templateEntities) {
            TemplateResponseVo responseVo = BeanUtil.toBean(loopTemplate, TemplateResponseVo.class);
            String content = null;
            try (
                    InputStream is = Files.newInputStream(Paths.get(globalProperties.getDeployTemplateFileBasePath() + File.separator + loopTemplate.getContentFileKey()));
            ) {
                content = IOUtils.toString(is, String.valueOf(StandardCharsets.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
            responseVo.setContent(content);
            dataList.add(responseVo);
        }
        return dataList;
    }

    public void deleteById(List<Integer> idList) {
        templateEntityMapper.deleteBatchIds(idList);
    }

    public void edit(EditTemplateRequestVo requestVo) {
        EasyTemplate template = templateEntityMapper.selectById(requestVo.getId());
        if (Objects.isNull(template)) {
            throw new AdminApiException(AdminApiFailureEnum.TEMPLATE_NOT_EXIST);
        }

        String contentFileKey = template.getContentFileKey();
        String contentFilePath = globalProperties.getDeployTemplateFileBasePath() + File.separator + contentFileKey;
        FileUtil.mkdir(FileUtil.getParent(contentFilePath, 1));
        try (
                InputStream is = new ByteArrayInputStream(requestVo.getContent().getBytes(StandardCharsets.UTF_8));
                OutputStream os = Files.newOutputStream(Paths.get(contentFilePath))
        ) {
            IoUtil.copy(is, os);
        } catch (Exception e) {
            throw new AdminApiException(AdminApiFailureEnum.TEMPLATE_NAME_EXIST);
        }

        EasyTemplate editEntity = new EasyTemplate();
        editEntity.setName(requestVo.getName());
        editEntity.setType(requestVo.getType());
        editEntity.setContentFileKey(contentFileKey);
        editEntity.setId(requestVo.getId());
        templateEntityMapper.updateById(editEntity);
    }
}
