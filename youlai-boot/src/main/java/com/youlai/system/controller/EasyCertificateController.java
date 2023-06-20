package com.youlai.system.controller;

import com.easy.core.controller.BaseController;
import com.easy.core.domain.ApiResult;
import com.youlai.system.common.enums.AdminApiFailureEnum;
import com.youlai.system.common.exception.AdminApiException;
import com.youlai.system.model.vo.request.AddCertificateRequestVo;
import com.youlai.system.model.vo.request.AddK8sCertificateRequestVo;
import com.youlai.system.model.vo.request.AddSshCertificateRequestVo;
import com.youlai.system.model.vo.request.EditCertificateRequestVo;
import com.youlai.system.model.vo.response.CertificateResponseVo;
import com.youlai.system.service.impl.EasyCertificateServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Tag(name = "凭证管理")
@RestController
@RequestMapping("/certificate")
@RequiredArgsConstructor
public class EasyCertificateController extends BaseController {

    @Autowired
    private EasyCertificateServiceImpl certificateService;

    private static final Pattern SSH_PRIVATE_KEY_PATTERN = Pattern.compile("^-----BEGIN ((EC|OPENSSH|DSA|RSA|SSH2)? )?(PRIVATE|ENCRYPTED) KEY-----(\\r?\\n)(.*\\r?\\n)*-----END ((EC|OPENSSH|DSA|RSA|SSH2)? )?(PRIVATE|ENCRYPTED) KEY-----\\r?\\n?$");

    public static boolean isValid(String sshKey) {
        return SSH_PRIVATE_KEY_PATTERN.matcher(sshKey).matches();
    }

    /**
     * 更新ssh 全局凭证
     */
    @PutMapping("/updateSshPrivateKey")
    public ApiResult<Void> updateSshPrivateKey(@RequestBody AddSshCertificateRequestVo requestVo) {
        String privateKey = requestVo.getSshPrivateKey();
        if (!isValid(privateKey)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        String osName = System.getProperty("os.name");
        if(osName.toLowerCase().contains("windows") || osName.toLowerCase().contains("mac")){
            throw new AdminApiException(AdminApiFailureEnum.CERTIFICATE_NEED_CONFIG_BY_YOURSELF);
        }

        certificateService.updateSshPrivateKey(privateKey);
        return success();
    }

    /**
     * 更新k8s配置
     */
    @PutMapping("/updateK8sConfig")
    public ApiResult<Void> updateK8sConfig(@RequestBody AddK8sCertificateRequestVo requestVo) {
        String kubeConfig = requestVo.getKubeConfig();
        if (StringUtils.isBlank(kubeConfig)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        String osName = System.getProperty("os.name");
        if(osName.toLowerCase().contains("windows") || osName.toLowerCase().contains("mac")){
            throw new AdminApiException(AdminApiFailureEnum.CERTIFICATE_NEED_CONFIG_BY_YOURSELF);
        }

        certificateService.updateK8sConfig(kubeConfig);
        return success();
    }

    /**
     * 新增凭证信息
     */
    @PostMapping("/add")
    public ApiResult<Integer> add(@RequestBody AddCertificateRequestVo requestVo) {
        String name = requestVo.getName();
        Integer repositoryType = requestVo.getRepositoryType();
        if (StringUtils.isBlank(name) || Objects.isNull(repositoryType)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        Integer id = certificateService.add(requestVo);
        return success(id);
    }

    /**
     * 修改凭证信息
     */
    @PutMapping("/edit")
    public ApiResult<Void> edit(@RequestBody EditCertificateRequestVo requestVo) {
        Integer repositoryType = requestVo.getRepositoryType();
        if (Objects.isNull(repositoryType)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        certificateService.edit(requestVo);
        return success();
    }

    /**
     * 删除凭证信息
     */
    @DeleteMapping("/deleteById")
    public ApiResult<Void> deleteById(Integer id) {
        if (Objects.isNull(id)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        certificateService.deleteById(id);
        return success();
    }

    /**
     * 获取凭证信息
     */
    @GetMapping("/findAll")
    public ApiResult<List<CertificateResponseVo>> findAll(@RequestParam(required = false,defaultValue = "true") boolean containSsh) {
        List<CertificateResponseVo> data = certificateService.findAll(containSsh);
        return success(data);
    }

}
