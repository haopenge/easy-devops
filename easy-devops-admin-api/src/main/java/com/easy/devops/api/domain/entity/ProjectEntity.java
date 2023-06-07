package com.easy.devops.api.domain.entity;

import java.util.Date;
import lombok.Data;

@Data
public class ProjectEntity {
    private Integer id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

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
     * git项目分支
     */
    private String branch;
}