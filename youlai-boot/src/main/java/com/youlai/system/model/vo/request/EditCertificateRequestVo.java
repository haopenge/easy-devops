package com.youlai.system.model.vo.request;

import lombok.Data;

/**
 * 修改凭证请求
 *
 * @author liuph
 */
@Data
public class EditCertificateRequestVo {

    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 认证秘钥
     */
    private String accessToken;

    /**
     * 描述信息
     */
    private String description;

    /**
     * ssh私钥
     */
    private Integer repositoryType;
}
