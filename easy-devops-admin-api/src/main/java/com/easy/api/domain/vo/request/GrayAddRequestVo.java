package com.easy.api.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class GrayAddRequestVo {

    /**
     * 独立环境名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date expireTime;
}
