package com.easy.devops.api.domain.entity;

import java.util.Date;
import lombok.Data;

@Data
public class RepositoryEntity {
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
     * 描述
     */
    private String description;

    /**
     * 项目名
     */
    private String name;

    /**
     * git项目分支
     */
    private String branch;

    /**
     * Git 克隆地址
     */
    private String cloneUrl;

    /**
     * 最新提交的hashCode
     */
    private String hashCode;

    /**
     * 认证id
     */
    private Integer easyAuthenticateId;
}