package com.easy.domain.entity;

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

    private LocalDateTime expire;

    private String extObj;
}
