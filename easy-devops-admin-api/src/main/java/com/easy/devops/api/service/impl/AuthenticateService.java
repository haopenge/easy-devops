package com.easy.devops.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.IoUtil;
import com.easy.devops.api.config.properties.GlobalProperties;
import com.easy.devops.api.domain.bo.HostAndUsername;
import com.easy.devops.api.domain.entity.AuthenticateEntity;
import com.easy.devops.api.domain.entity.AuthenticateEntityExample;
import com.easy.devops.api.domain.enumx.AuthenticateTypeEnum;
import com.easy.devops.api.domain.vo.request.AddAuthenticateRequestVo;
import com.easy.devops.api.domain.vo.request.EditAuthenticateRequestVo;
import com.easy.devops.api.domain.vo.response.AuthenticateResponseVo;
import com.easy.devops.api.domain.vo.response.RepositoryResponseVo;
import com.easy.devops.api.exception.AdminApiException;
import com.easy.devops.api.mapper.AuthenticateEntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Host;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.easy.devops.api.domain.enumx.AdminApiFailureEnum.AUTHENTICATE_NAME_EXISTS;
import static com.easy.devops.api.domain.enumx.AdminApiFailureEnum.AUTHENTICATE_NOT_EXISTS;
import static com.easy.devops.api.domain.enumx.AdminApiFailureEnum.AUTHENTICATE_SSH_PRIVATE_KEY_SAVE_ERROR;

/**
 * 凭证
 *
 * @author liuph
 */
@Slf4j
@Service
public class AuthenticateService {

    @Resource
    private AuthenticateEntityMapper authenticateEntityMapper;

    @Resource
    private GlobalProperties globalProperties;

    /**
     * 新增凭证
     *
     * @param requestVo 凭证请求vo
     * @return 新增成功后id
     */
    public Integer add(AddAuthenticateRequestVo requestVo) {
        AuthenticateEntity addEntity = new AuthenticateEntity();
        addEntity.setName(requestVo.getName());
        addEntity.setDescription(requestVo.getDescription());
        addEntity.setType(requestVo.getType());

        AuthenticateEntityExample example = new AuthenticateEntityExample();
        example.createCriteria().andNameEqualTo(requestVo.getName());
        List<AuthenticateEntity> authenticateEntities = authenticateEntityMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(authenticateEntities)) {
            throw new AdminApiException(AUTHENTICATE_NAME_EXISTS);
        }

        // 用户名、密码凭证
        if (requestVo.getType() == AuthenticateTypeEnum.USER_PWD.getValue()) {
            addEntity.setUsername(requestVo.getUsername());
            addEntity.setPassword(requestVo.getPassword());
        } else {
            String sshPrivateKey = requestVo.getSshPrivateKey();
            String fileName = UUID.randomUUID().toString();
            addEntity.setSshPrivateKey(sshPrivateKey);
            addEntity.setSshPrivateKeyFileName(fileName);
            String privateKeyPath = globalProperties.getAuthSshPrivateKeyPath() + File.separator + fileName;

            // 存储 ssh文件到服务器
            try (InputStream is = new ByteArrayInputStream(sshPrivateKey.getBytes(StandardCharsets.UTF_8)); OutputStream os = Files.newOutputStream(new File(privateKeyPath).toPath())) {
                IoUtil.copy(is, os);
            } catch (Exception e) {
                log.error(AUTHENTICATE_SSH_PRIVATE_KEY_SAVE_ERROR.getMessage(), e);
                throw new AdminApiException(AUTHENTICATE_SSH_PRIVATE_KEY_SAVE_ERROR);
            }
        }
        authenticateEntityMapper.insertSelective(addEntity);
        return addEntity.getId();
    }

    /**
     * 编辑凭证
     *
     * @param requestVo 请求
     */
    public void edit(EditAuthenticateRequestVo requestVo) {
        AuthenticateEntity dbEntity = authenticateEntityMapper.selectByPrimaryKey(requestVo.getId());
        if (Objects.isNull(dbEntity)) {
            throw new AdminApiException(AUTHENTICATE_NOT_EXISTS);
        }

        AuthenticateEntity updateEntity = new AuthenticateEntity();
        updateEntity.setId(requestVo.getId());

        // 用户名、密码凭证
        if (requestVo.getType() == AuthenticateTypeEnum.USER_PWD.getValue()) {
            updateEntity.setUsername(requestVo.getUsername());
            updateEntity.setPassword(requestVo.getPassword());
        } else {
            String sshPrivateKey = requestVo.getSshPrivateKey();
            String fileName = UUID.randomUUID().toString();
            updateEntity.setSshPrivateKey(sshPrivateKey);
            updateEntity.setSshPrivateKeyFileName(fileName);

            String privateKeyPath = globalProperties.getAuthSshPrivateKeyPath() + File.separator + fileName;
            // 存储 ssh文件到服务器
            try (InputStream is = new ByteArrayInputStream(sshPrivateKey.getBytes(StandardCharsets.UTF_8)); OutputStream os = Files.newOutputStream(new File(privateKeyPath).toPath())) {
                IoUtil.copy(is, os);
            } catch (Exception e) {
                log.error(AUTHENTICATE_SSH_PRIVATE_KEY_SAVE_ERROR.getMessage(), e);
                throw new AdminApiException(AUTHENTICATE_SSH_PRIVATE_KEY_SAVE_ERROR);
            }
        }
        authenticateEntityMapper.updateByPrimaryKey(updateEntity);
    }

    /**
     * 获取凭证列表
     *
     * @return AuthenticateResponseVo
     */
    public List<AuthenticateResponseVo> findAll() {
        List<AuthenticateEntity> entityList = authenticateEntityMapper.selectByExample(new AuthenticateEntityExample());
        return BeanUtil.copyToList(entityList, AuthenticateResponseVo.class);
    }

    /**
     * 删除凭证信息
     *
     * @param id 凭证id
     */
    public void deleteById(Integer id) {
        AuthenticateEntity dbEntity = authenticateEntityMapper.selectByPrimaryKey(id);
        if (Objects.isNull(dbEntity)) {
            throw new AdminApiException(AUTHENTICATE_NOT_EXISTS);
        }
        authenticateEntityMapper.deleteByPrimaryKey(id);
    }
}
