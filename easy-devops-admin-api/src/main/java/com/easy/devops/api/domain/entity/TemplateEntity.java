package com.easy.devops.api.domain.entity;

import java.util.Date;
import lombok.Data;

@Data
public class TemplateEntity {
    /**
     * Id
     */
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