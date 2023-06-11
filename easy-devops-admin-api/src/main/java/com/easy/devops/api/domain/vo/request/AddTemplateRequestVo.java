package com.easy.devops.api.domain.vo.request;

import lombok.Data;

import java.util.Date;

@Data
public class AddTemplateRequestVo {

    /**
     * 名称
     */
    private String name;

    /**
     * 模板类型：1-shell；5-dockerfile ;  10-yaml ;
     */
    private Integer type;

    /**
     * 内容
     */
    private String content;

}
