package com.easy.devops.api.domain.entity;

import java.util.Date;
import lombok.Data;

@Data
public class BuildEntity {
    /**
     * Id
     */
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
     * 仓库id
     */
    private Integer easyRepositoryId;

    /**
     * 构建顺序，执行时由小到大
     */
    private Integer order;

    /**
     * 构建类型：0-检出代码；5-脚本执行；10-镜像构建；15-发布项目
     */
    private Integer type;

    /**
     * 构建时的hash值
     */
    private String hashCode;

    /**
     * 内容
     */
    private String content;
}