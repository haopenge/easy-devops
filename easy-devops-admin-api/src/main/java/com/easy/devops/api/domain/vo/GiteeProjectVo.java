package com.easy.devops.api.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * gitee 项目获取vo, 因返回字段过多，这里仅接受我们感兴趣的；
 * @author liuph
 */
@Data
public class GiteeProjectVo {

    private Integer id;

    private String name;

    @JsonProperty("full_name")
    private String fullName;

    private String description;

    @JsonProperty("html_url")
    private String cloneUrl;

}
