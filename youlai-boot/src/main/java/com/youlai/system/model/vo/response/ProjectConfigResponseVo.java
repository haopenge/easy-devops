package com.youlai.system.model.vo.response;

import lombok.Data;

/**
 * 项目配置vo
 *
 * @author liuph
 */
@Data
public class ProjectConfigResponseVo {

    /**
     * 项目id
     */
    private Integer id;

    /**
     * 挂载模板id
     */
    private String easyTemplateIds;

    /**
     * 镜像构建脚本
     */
    private String dockerScript;

    /**
     * 发布脚本
     */
    private String pushScript;

}
