package com.easy.devops.api.domain.vo.request;

import lombok.Data;

/**
 * 新增ssh凭证
 *
 * @author liuph
 */
@Data
public class AddK8sCertificateRequestVo {

    /**
     * kube config
     */
    private String kubeConfig;
}
