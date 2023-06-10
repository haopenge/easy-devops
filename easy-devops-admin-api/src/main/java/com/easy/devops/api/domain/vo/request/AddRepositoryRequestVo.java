package com.easy.devops.api.domain.vo.request;

import lombok.Data;

/**
 * 新增仓库请求
 *
 * @author liuph
 */
@Data
public class AddRepositoryRequestVo {
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
     * 分支
     */
    private String branch;
}
