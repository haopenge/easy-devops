package com.easy.devops.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.UUID;
import com.easy.devops.api.config.properties.GlobalProperties;
import com.easy.devops.api.domain.entity.TemplateEntity;
import com.easy.devops.api.domain.entity.TemplateEntityExample;
import com.easy.devops.api.domain.enumx.AdminApiFailureEnum;
import com.easy.devops.api.domain.enumx.TemplateTypeEnum;
import com.easy.devops.api.domain.vo.request.AddTemplateRequestVo;
import com.easy.devops.api.domain.vo.response.TemplateResponseVo;
import com.easy.devops.api.exception.AdminApiException;
import com.easy.devops.api.mapper.TemplateEntityMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class TemplateService {

    @Resource
    private TemplateEntityMapper templateEntityMapper;

    @Resource
    private GlobalProperties globalProperties;

    public Integer add(AddTemplateRequestVo requestVo) {
        TemplateEntityExample example = new TemplateEntityExample();
        example.createCriteria().andNameEqualTo(requestVo.getName())
                .andTypeEqualTo(requestVo.getType());

        List<TemplateEntity> templateEntities = templateEntityMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(templateEntities)) {
            throw new AdminApiException(AdminApiFailureEnum.TEMPLATE_NAME_EXIST);
        }

        String contentFileKey = UUID.fastUUID().toString();
        String contentFilePath = globalProperties.getDeployTemplateFileBasePath() + File.separator + contentFileKey;
        FileUtil.mkdir(FileUtil.getParent(contentFilePath,1));
        try (
                InputStream is = new ByteArrayInputStream(requestVo.getContent().getBytes(StandardCharsets.UTF_8));
                OutputStream os = Files.newOutputStream(Paths.get(contentFilePath))
                ){
            IoUtil.copy(is, os);
        }catch (Exception e){
            throw new AdminApiException(AdminApiFailureEnum.TEMPLATE_NAME_EXIST);
        }

        TemplateEntity addEntity = new TemplateEntity();
        addEntity.setName(requestVo.getName());
        addEntity.setType(requestVo.getType());
        addEntity.setContentFileKey(contentFileKey);
        templateEntityMapper.insertSelective(addEntity);
        return addEntity.getId();
    }

    public List<TemplateResponseVo> findAll() {
        List<TemplateEntity> templateEntities = templateEntityMapper.selectByExample(new TemplateEntityExample());
        if(CollectionUtils.isEmpty(templateEntities)){
            return Collections.emptyList();
        }
        List<TemplateResponseVo> dataList = new ArrayList<>();
        for (TemplateEntity loopTemplate : templateEntities) {
            TemplateResponseVo responseVo = BeanUtil.toBean(loopTemplate, TemplateResponseVo.class);
            TemplateTypeEnum templateTypeEnum = TemplateTypeEnum.valueOf(loopTemplate.getType());
            if(Objects.nonNull(templateTypeEnum)){
                responseVo.setTypeShow(templateTypeEnum.getDescription());
            }
            String content = null;
            try (
                    InputStream is = Files.newInputStream(Paths.get(globalProperties.getDeployTemplateFileBasePath() + File.separator + loopTemplate.getContentFileKey()));
            ){
                content = IOUtils.toString(is, String.valueOf(StandardCharsets.UTF_8));
            }catch (Exception e){
                e.printStackTrace();
            }
            responseVo.setContent(content);
            dataList.add(responseVo);
        }
        return dataList;
    }

    public void deleteById(Integer id) {
        TemplateEntity templateEntity = templateEntityMapper.selectByPrimaryKey(id);
        if(Objects.isNull(templateEntity)){
            throw new AdminApiException(AdminApiFailureEnum.TEMPLATE_NOT_EXIST);
        }
        templateEntityMapper.deleteByPrimaryKey(id);
        File contentFile = new File(globalProperties.getDeployTemplateFileBasePath() + File.separator + templateEntity.getContentFileKey());
        if(contentFile.exists()){
            contentFile.delete();
        }
    }
}
