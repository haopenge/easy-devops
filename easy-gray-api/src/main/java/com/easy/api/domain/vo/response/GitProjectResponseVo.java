
package com.easy.api.domain.vo.response;

import lombok.Data;

/**
 * git 项目获取vo, 因返回字段过多，这里仅接受我们感兴趣的；
 */
@Data
public class GitProjectResponseVo {

    private Integer id;

    private String name;

    private String fullName;

    private String description;

    private String cloneUrl;
}
