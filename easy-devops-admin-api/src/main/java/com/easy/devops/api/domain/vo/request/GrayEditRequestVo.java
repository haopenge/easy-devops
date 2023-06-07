package com.easy.devops.api.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class GrayEditRequestVo {

    /**
     * 环境id
     */
    private Integer id;

    /**
     * 描述
     */
    private String description;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date expireTime;
}
