package com.easy.api.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class GrayEnvResponseVo {

    private Integer id;

    private Date createTime;

    private Date updateTime;

    private String description;

    private String name;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date expireTime;
}
