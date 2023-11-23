package com.youlai.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 环境
 * @TableName easy_env
 */
@TableName(value ="easy_env")
@Data
public class EasyEnv implements Serializable {
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
     * 描述
     */
    private String description;

    /**
     * 灰度环境名称
     */
    private String name;

    /**
     * 有效时间，此时间为截止日期
     */
    private LocalDateTime expireTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
