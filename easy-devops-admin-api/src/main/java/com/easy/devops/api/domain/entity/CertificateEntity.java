package com.easy.devops.api.domain.entity;

import java.util.Date;
import lombok.Data;

@Data
public class CertificateEntity {
    /**
     * Id
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

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
     * 认证秘钥
     */
    private String accessToken;

    /**
     * git仓库类型，1-github  2-gitee   3-gitlab

     */
    private Integer repositoryType;
}