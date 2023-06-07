
package com.easy.devops.api.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * github 项目获取vo, 因返回字段过多，这里仅接受我们感兴趣的；
 */
@Data
public class GithubProjectVo {

    private Integer id;

    private String name;

    @JsonProperty("full_name")
    private String fullName;

    private String description;

    @JsonProperty("clone_url")
    private String cloneUrl;
}
