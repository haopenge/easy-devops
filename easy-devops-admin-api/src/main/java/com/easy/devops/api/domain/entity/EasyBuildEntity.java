package com.easy.devops.api.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "easy_build")
@ApiModel("easy_build")
public class EasyBuildEntity {
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
     * 内容
     */
    private String content;
}
