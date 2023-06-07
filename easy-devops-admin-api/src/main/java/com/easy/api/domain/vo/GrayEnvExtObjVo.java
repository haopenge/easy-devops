package com.easy.api.domain.vo;

import lombok.Data;

/**
 * 灰度环境扩展信息Vo
 */
@Data
public class GrayEnvExtObjVo {

    /**
     * 项目id
     */
    private String id;

    /**
     * git项目名
     */
    private String gitName;

    /**
     * 子项目包路径，项目为子项目时填写
     */
    private String subProjectPath;

    /**
     * git 项目分支
     */
    private String branch;

    /**
     * git 克隆地址
     */
    private String cloneUrl;

}
