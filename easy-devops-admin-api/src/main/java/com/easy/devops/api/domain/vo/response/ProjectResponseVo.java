package com.easy.devops.api.domain.vo.response;

import lombok.Data;

import java.util.Date;

/**
 * 项目vo
 */
@Data
public class ProjectResponseVo {

    private Integer id;

    /**
     * 项目名
     */
    private String name;

    /**
     * 环境id
     */
    private Integer easyEnvId;

    /**
     * 仓库id
     */
    private Integer easyRepositoryId;

    /**
     * 仓库名称
     */
    private String easyRepositoryName;

    /**
     * 凭证名称
     */
    private String easyCertificateName;

    /**
     * git项目分支
     */
    private String branch;
}
