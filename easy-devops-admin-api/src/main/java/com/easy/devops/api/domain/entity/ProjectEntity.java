package com.easy.devops.api.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "easy_project")
@ApiModel("easy_project")
public class ProjectEntity {
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
     * 项目名
     */
    private String name;

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
     * git项目分支
     */
    private String branch;
}