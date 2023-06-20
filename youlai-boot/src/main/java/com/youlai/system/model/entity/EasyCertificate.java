package com.youlai.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 凭证
 * @TableName easy_certificate
 */
@TableName(value ="easy_certificate")
@Data
public class EasyCertificate implements Serializable {
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
     * 描述信息
     */
    private String description;

    /**
     * 用户名
     */
    private String username;

    /**
     * 认证秘钥
     */
    private String accessToken;

    /**
     * git仓库类型，1-github  2-gitee   3-gitlab

     */
    private Integer repositoryType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
