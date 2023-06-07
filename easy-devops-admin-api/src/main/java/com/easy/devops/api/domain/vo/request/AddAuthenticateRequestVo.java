package com.easy.devops.api.domain.vo.request;

import lombok.Data;

/**
 * 新增认证请求
 *
 * @author liuph
 */
@Data
public class AddAuthenticateRequestVo {

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
     * ssh私钥
     */
    private String sshPrivateKey;
}
