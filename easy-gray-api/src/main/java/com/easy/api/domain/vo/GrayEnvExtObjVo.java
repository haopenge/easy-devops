package com.easy.api.domain.vo;

import lombok.Data;

/**
 * 灰度环境扩展信息Vo
 */
@Data
public class GrayEnvExtObjVo {

    private String name;

    private String branch;

    private String cloneUrl;

}
