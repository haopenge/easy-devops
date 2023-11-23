package com.youlai.system.model.vo.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddEnvRequestVo {

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
