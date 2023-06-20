package com.youlai.system.model.vo.response;

import lombok.Data;

@Data
public class TemplateResponseVo {

    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 模板类型：1-shell；5-dockerfile ;  10-yaml ;
     */
    private Integer type;

    /**
     * 模板展示信息
     */
    private String typeShow;

    /**
     * 内容
     */
    private String content;
}
