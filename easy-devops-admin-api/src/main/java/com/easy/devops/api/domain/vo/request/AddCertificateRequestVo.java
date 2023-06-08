package com.easy.devops.api.domain.vo.request;

import lombok.Data;

/**
 * 新增认证请求
 *
 * @author liuph
 */
@Data
public class AddCertificateRequestVo {

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
     * 认证令牌
     */
    private String accessToken;

    /**
     * git仓库类型，1-github  2-gitee   3-gitlab
     */
    private Integer repositoryType;
}
