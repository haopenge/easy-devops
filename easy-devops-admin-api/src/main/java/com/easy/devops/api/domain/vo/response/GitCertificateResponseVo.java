package com.easy.devops.api.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * git 项目获取vo, 因返回字段过多，这里仅接受我们感兴趣的；
 *
 * @author liupenghao
 */
@Data
public class GitCertificateResponseVo {

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
    private Boolean isPublic;

    /**
     * 默认分支
     */
    private String defaultBranch;

    /**
     * http clone地址
     */
    private String httpCloneUrl;

    /**
     * ssh clone地址
     */
    private String sshCloneUrl;
}
