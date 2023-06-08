package com.easy.devops.api.domain.entity;

import java.util.Date;
import lombok.Data;

@Data
public class AuthenticateEntity {
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
     * 密码
     */
    private String password;

    /**
     * 认证类型：1-账号密码； 5-ssh私钥
     */
    private Integer type;

    /**
     * ssh私钥本地存储文件名
     */
    private String sshPrivateKeyFileName;

    /**
     * ssh私钥
     */
    private String sshPrivateKey;
}