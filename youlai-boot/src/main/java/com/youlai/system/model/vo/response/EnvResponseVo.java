package com.youlai.system.model.vo.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnvResponseVo {

    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
}
