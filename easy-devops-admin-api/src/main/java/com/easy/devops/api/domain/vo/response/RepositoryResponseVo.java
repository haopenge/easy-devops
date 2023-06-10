package com.easy.devops.api.domain.vo.response;

import lombok.Data;

/**
 * 仓库响应vo
 *
 * @author liuph
 */
@Data
public class RepositoryResponseVo {

    private Integer id;

    /**
     * 描述
     */
    private String description;

    /**
     * 项目名
     */
    private String name;

    /**
     * Git 克隆地址
     */
    private String cloneUrl;

    /**
     * 凭证id
     */
    private Integer easyCertificateId;

    /**
     * 凭证名称
     */
    private String easyCertificateName;

    /**
     * 分支
     */
    private String branch;

}
