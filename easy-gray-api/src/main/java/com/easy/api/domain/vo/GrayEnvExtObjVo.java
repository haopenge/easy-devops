package com.easy.api.domain.vo;

import lombok.Data;

/**
 * 灰度环境扩展信息Vo
 */
@Data
public class GrayEnvExtObjVo {

    /**
     * 灰度环境名称
     */
    private String name;

    /**
     * git 项目名称
     */
    private String gitName;

    /**
     * 项目包路径，项目为子项目时填写
     */
    private String packagePath;

    /**
     * git 项目分支
     */
    private String branch;

    /**
     * git 克隆地址
     */
    private String cloneUrl;

}
