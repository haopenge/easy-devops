package com.easy.devops.api.domain.entity;

import java.util.Date;
import lombok.Data;

@Data
public class EnvEntity {
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
     * 灰度环境名称
     */
    private String name;

    /**
     * 有效时间，此时间为截止日期
     */
    private Date expireTime;
}