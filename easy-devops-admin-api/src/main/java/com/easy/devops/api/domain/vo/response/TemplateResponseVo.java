package com.easy.devops.api.domain.vo.response;

import lombok.Data;

import java.util.Date;

@Data
public class TemplateResponseVo {

    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 模板类型：1-shell；5-dockerfile ;  10-yaml ;
     */
    private Integer type;

    /**
     * 内容
     */
    private String content;
}
