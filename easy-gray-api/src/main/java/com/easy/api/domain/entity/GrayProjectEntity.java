package com.easy.api.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "gray_project")
@ApiModel("gray_project")
public class GrayProjectEntity {
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
     * 灰度环境id
     */
    @Column(name = "gray_env_id")
    @ApiModelProperty("灰度环境id")
    private Integer grayEnvId;

    /**
     * 描述
     */
    private String description;

    /**
     * 项目名
     */
    @Column(name = "`name`")
    @ApiModelProperty("项目名")
    private String name;

    /**
     * git全路径
     */
    @Column(name = "full_name")
    @ApiModelProperty("git全路径")
    private String fullName;

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
     * 运行状态
     */
    @Column(name = "`status`")
    @ApiModelProperty("运行状态")
    private Integer status;

    /**
     * git项目名
     */
    @Column(name = "git_name")
    @ApiModelProperty("git项目名")
    private String gitName;
}
