package com.easy.api.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "gray_env")
@ApiModel("gray_env")
public class GrayEnvEntity {
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
     * 1正常 2回收站资源 3 彻底删除资源
     */
    @Column(name = "`state`")
    @ApiModelProperty("1正常 2回收站资源 3 彻底删除资源")
    private Integer state;

    /**
     * 描述
     */
    private String description;

    /**
     * 灰度环境名称
     */
    @Column(name = "`name`")
    @ApiModelProperty("灰度环境名称")
    private String name;

    /**
     * 有效时间，此时间为截止日期
     */
    @Column(name = "expire_time")
    @ApiModelProperty("有效时间，此时间为截止日期")
    private Date expireTime;

    /**
     * 扩展信息，存储灰度的服务，分支
     */
    @Column(name = "ext_obj")
    @ApiModelProperty("扩展信息，存储灰度的服务，分支")
    private String extObj;
}
