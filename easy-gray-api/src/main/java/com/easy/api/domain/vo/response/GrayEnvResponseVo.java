package com.easy.api.domain.vo.response;

import com.easy.api.domain.vo.GrayEnvExtObjVo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GrayEnvResponseVo {

    private Integer id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String description;

    private String name;

    private LocalDateTime expireTime;

    private List<GrayEnvExtObjVo> extObjList;
}
