package com.youlai.system.model.vo.response;

import lombok.Data;

/**
 * 认证响应vo
 *
 * @author liuph
 */
@Data
public class CertificateResponseVo {

    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 用户名
     */
    private String username;

    /**
     * 1-全局ssh  5-用户名密码令牌
     */
    private Integer type;

    /**
     * git仓库类型，1-github  2-gitee   3-gitlab
     */
    private Integer repositoryType;

}
