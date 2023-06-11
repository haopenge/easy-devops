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
     * 名称
     */
    private String name;

    /**
     * 内容
     */
    private String contentFileKey;

    /**
     * 模板类型：1-dockerfile ；5-shell;  10-yaml ; 
     */
    private Integer type;
}