package com.youlai.system.model.vo.request;

import lombok.Data;

/**
 * 新增ssh凭证
 *
 * @author liuph
 */
@Data
public class AddSshCertificateRequestVo {

    /**
     * ssh私钥
     */
    private String sshPrivateKey;
}
