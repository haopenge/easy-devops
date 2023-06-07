package com.easy.api.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * gitlab 项目获取vo, 因返回字段过多，这里仅接受我们感兴趣的；
 */
@Data
public class GitlabProjectVo {

    private Integer id;

    @JsonProperty("path")
    private String name;

    @JsonProperty("path_with_namespace")
    private String fullName;

    private String description;

    @JsonProperty("import_url")
    private String cloneUrl;
}
