package com.easy.devops.api.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "easy_env")
@ApiModel("easy_env")
public class EnvEntity {
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
     * 灰度环境名称
     */
    private String name;

    /**
     * 有效时间，此时间为截止日期
     */
    @Column(name = "expire_time")
    @ApiModelProperty("有效时间，此时间为截止日期")
    private Date expireTime;
}