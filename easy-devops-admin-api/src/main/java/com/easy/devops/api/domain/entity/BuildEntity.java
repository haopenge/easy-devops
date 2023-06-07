package com.easy.devops.api.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "easy_build")
@ApiModel("easy_build")
public class BuildEntity {
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
     * 仓库id
     */
    @Column(name = "easy_repository_id")
    @ApiModelProperty("仓库id")
    private Integer easyRepositoryId;

    /**
     * 构建顺序，执行时由小到大
     */
    private Integer order;

    /**
     * 构建类型：0-检出代码；5-脚本执行；10-镜像构建；15-发布项目
     */
    private Integer type;

    /**
     * 构建时的hash值
     */
    @Column(name = "hash_code")
    @ApiModelProperty("构建时的hash值")
    private String hashCode;

    /**
     * 内容
     */
    private String content;
}