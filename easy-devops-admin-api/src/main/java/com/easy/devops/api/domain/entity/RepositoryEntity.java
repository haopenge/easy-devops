package com.easy.devops.api.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "easy_repository")
@ApiModel("easy_repository")
public class RepositoryEntity {
    /**
     * Id
     */
    @Id
    private Integer id;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @ApiModelProperty("更新时间")
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
    @Column(name = "clone_url")
    @ApiModelProperty("Git 克隆地址")
    private String cloneUrl;

    /**
     * 最新提交的hashCode
     */
    @Column(name = "hash_code")
    @ApiModelProperty("最新提交的hashCode")
    private String hashCode;

    /**
     * 认证id
     */
    @Column(name = "easy_authenticate_id")
    @ApiModelProperty("认证id")
    private Integer easyAuthenticateId;
}