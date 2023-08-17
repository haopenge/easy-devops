package com.youlai.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 项目
 * @TableName easy_project
 */
@TableName(value ="easy_project")
@Data
public class EasyProject implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}