package com.easy.devops.api.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "easy_project")
@ApiModel("easy_project")
public class EasyProjectEntity {
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
     * 环境id
     */
    @Column(name = "easy_env_id")
    @ApiModelProperty("环境id")
    private Integer easyEnvId;

    /**
     * 仓库id
     */
    @Column(name = "easy_repository_id")
    @ApiModelProperty("仓库id")
    private Integer easyRepositoryId;

    /**
     * 项目名
     */
    private String name;

    /**
     * 构建路径
     */
    @Column(name = "build_path")
    @ApiModelProperty("构建路径")
    private String buildPath;

    /**
     * git项目分支
     */
    private String branch;

    /**
     * 构建时所应用的hashCode
     */
    @Column(name = "hash_code")
    @ApiModelProperty("构建时所应用的hashCode")
    private String hashCode;
}
