package com.easy.api.domain.vo.response;

import lombok.Data;

import java.util.Date;

@Data
public class GrayEnvResponseVo {

    private Integer id;

    private Date createTime;

    private Date updateTime;

    private String description;

    private String name;

    private Date expireTime;
}
