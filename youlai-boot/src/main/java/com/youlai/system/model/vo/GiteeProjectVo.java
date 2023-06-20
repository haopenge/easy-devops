package com.youlai.system.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * gitee 项目获取vo, 因返回字段过多，这里仅接受我们感兴趣的；
 * @author liuph
 */
@Data
public class GiteeProjectVo {

    /**
     * 仓库id
     */
    private Integer id;

    /**
     * 仓库名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否是公共仓库
     */
    @JsonProperty("public")
    private Boolean isPublic;

    /**
     * 默认分支
     */
    @JsonProperty("default_branch")
    private String defaultBranch;

    /**
     * http clone地址
     */
    @JsonProperty("html_url")
    private String httpCloneUrl;

    /**
     * ssh clone地址
     */
    @JsonProperty("ssh_url")
    private String sshCloneUrl;

}
