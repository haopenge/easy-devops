package com.easy.devops.api.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "easy_template")
@ApiModel("easy_template")
public class TemplateEntity {
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
     * 构建顺序，执行时由小到大
     */
    private Integer order;

    /**
     * 模板类型：1-shell；5-dockerfile ;  10-yaml ;
     */
    private Integer type;

    /**
     * 内容
     */
    private String content;
}
