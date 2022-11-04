package com.easy.api.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GrayEnvEntity {

    private Integer id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer state;

    private String description;

    private String name;

    private LocalDateTime expireTime;

    private String extObj;
}
