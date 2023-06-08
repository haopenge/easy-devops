package com.easy.devops.api.domain.vo.request;

import lombok.Data;

/**
 * 修改仓库请求
 *
 * @author liuph
 */
@Data
public class EditRepositoryRequestVo {


    private Integer id;

    /**
     * 描述
     */
    private String description;

    /**
     * Git 克隆地址
     */
    private String cloneUrl;

    /**
     * 认证id
     */
    private Integer easyAuthenticateId;
}
