package com.youlai.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 模板
 * @TableName easy_template
 */
@TableName(value ="easy_template")
@Data
public class EasyTemplate implements Serializable {
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
     * 名称
     */
    private String name;

    /**
     * 内容
     */
    private String contentFileKey;

    /**
     * 模板类型：1-dockerfile ；5-shell;  10-yaml ; 
     */
    private Integer type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}