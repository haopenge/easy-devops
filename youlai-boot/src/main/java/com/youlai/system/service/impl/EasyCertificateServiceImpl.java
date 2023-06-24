package com.youlai.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.system.common.exception.AdminApiException;
import com.youlai.system.config.properties.GlobalProperties;
import com.youlai.system.mapper.EasyCertificateMapper;
import com.youlai.system.model.entity.EasyCertificate;
import com.youlai.system.model.vo.request.AddCertificateRequestVo;
import com.youlai.system.model.vo.request.EditCertificateRequestVo;
import com.youlai.system.model.vo.response.CertificateResponseVo;
import com.youlai.system.service.EasyCertificateService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static com.youlai.system.common.enums.AdminApiFailureEnum.CERTIFICATE_K8S_CONFIG_SAVE_ERROR;
import static com.youlai.system.common.enums.AdminApiFailureEnum.CERTIFICATE_NAME_EXISTS;
import static com.youlai.system.common.enums.AdminApiFailureEnum.CERTIFICATE_NOT_EXISTS;
import static com.youlai.system.common.enums.AdminApiFailureEnum.CERTIFICATE_SSH_PRIVATE_KEY_SAVE_ERROR;

/**
 * @author liupenghao
 * @description 针对表【easy_certificate(凭证)】的数据库操作Service实现
 * @createDate 2023-06-18 19:06:50
 */
@Service
public class EasyCertificateServiceImpl extends ServiceImpl<EasyCertificateMapper, EasyCertificate> implements EasyCertificateService {

    @Resource
    private EasyCertificateMapper easyCertificateMapper;

    @Resource
    private GlobalProperties globalProperties;

    /**
     * 新增凭证
     *
     * @param requestVo 凭证请求vo
     * @return 新增成功后id
     */
    public Integer add(AddCertificateRequestVo requestVo) {
        List<EasyCertificate> authenticateEntities = easyCertificateMapper
                .selectList(new LambdaQueryWrapper<EasyCertificate>()
                        .eq(EasyCertificate::getName,requestVo.getName()));
        if (!CollectionUtils.isEmpty(authenticateEntities)) {
            throw new AdminApiException(CERTIFICATE_NAME_EXISTS);
        }

        // 用户名、密码凭证
        EasyCertificate addEntity = new EasyCertificate();
        addEntity.setName(requestVo.getName());
        addEntity.setDescription(requestVo.getDescription());
        addEntity.setRepositoryType(requestVo.getRepositoryType());
        addEntity.setUsername(requestVo.getUsername());
        addEntity.setAccessToken(requestVo.getAccessToken());

        easyCertificateMapper.insert(addEntity);
        return addEntity.getId();
    }

    /**
     * 编辑凭证
     *
     * @param requestVo 请求
     */
    public void edit(EditCertificateRequestVo requestVo) {
        EasyCertificate dbEntity = easyCertificateMapper.selectById(requestVo.getId());
        if (Objects.isNull(dbEntity)) {
            throw new AdminApiException(CERTIFICATE_NOT_EXISTS);
        }

        EasyCertificate updateEntity = new EasyCertificate();
        updateEntity.setId(requestVo.getId());
        updateEntity.setName(requestVo.getName());
        updateEntity.setDescription(requestVo.getDescription());
        updateEntity.setRepositoryType(requestVo.getRepositoryType());

        // 用户名、密码凭证
        updateEntity.setUsername(requestVo.getUsername());
        updateEntity.setAccessToken(requestVo.getAccessToken());

        easyCertificateMapper.updateById(updateEntity);
    }

    /**
     * 获取凭证列表
     *
     * @return AuthenticateResponseVo
     */
    public List<CertificateResponseVo> findAll() {
        List<EasyCertificate> entityList = easyCertificateMapper.selectList(new LambdaQueryWrapper<>());
        return BeanUtil.copyToList(entityList, CertificateResponseVo.class);
    }

    /**
     * 删除凭证信息
     *
     * @param ids 凭证id
     */
    public void deleteById(List<Integer> ids) {
        easyCertificateMapper.deleteBatchIds(ids);
    }

    /**
     * 更新部署服务器ssh 私钥
     *
     * @param privateKey ssh 私钥
     */
    public void updateSshPrivateKey(String privateKey) {
        // 存储 ssh文件到服务器
        try (InputStream is = new ByteArrayInputStream(privateKey.getBytes(StandardCharsets.UTF_8)); OutputStream os = Files.newOutputStream(Paths.get(globalProperties.getAuthSshPrivateKeyFilePath()))) {
            IoUtil.copy(is, os);
        } catch (Exception e) {
            log.error(CERTIFICATE_SSH_PRIVATE_KEY_SAVE_ERROR.getMessage(), e);
            throw new AdminApiException(CERTIFICATE_SSH_PRIVATE_KEY_SAVE_ERROR);
        }
    }

    /**
     * 更新部署服务器 k8s 配置
     *
     * @param kubeConfig k8s配置
     */
    public void updateK8sConfig(String kubeConfig) {
        // 存储 ssh文件到服务器
        try (InputStream is = new ByteArrayInputStream(kubeConfig.getBytes(StandardCharsets.UTF_8)); OutputStream os = Files.newOutputStream(Paths.get(globalProperties.getK8sConfigFilePath()))) {
            IoUtil.copy(is, os);
        } catch (Exception e) {
            log.error(CERTIFICATE_K8S_CONFIG_SAVE_ERROR.getMessage(), e);
            throw new AdminApiException(CERTIFICATE_K8S_CONFIG_SAVE_ERROR);
        }
    }
}




