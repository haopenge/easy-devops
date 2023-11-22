package com.youlai.system.model.vo.request;

import lombok.Data;

/**
 * 更新项目配置vo
 *
 * @author liuph
 */
@Data
public class UpdateConfigVo {

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
