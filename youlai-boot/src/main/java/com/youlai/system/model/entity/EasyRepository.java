package com.youlai.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 仓库
 * @TableName easy_repository
 */
@TableName(value ="easy_repository")
@Data
public class EasyRepository implements Serializable {
    /**
     * Id
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
     * 描述
     */
    private String description;

    /**
     * 项目名
     */
    private String name;

    /**
     * git项目分支
     */
    private String branch;

    /**
     * Git 克隆地址
     */
    private String cloneUrl;

    /**
     * 认证id
     */
    private Integer easyCertificateId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
