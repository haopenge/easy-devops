package com.youlai.system.model.vo.request;

import lombok.Data;

/**
 * 新增仓库请求
 *
 * @author liuph
 */
@Data
public class AddRepositoryRequestVo {

    /**
     * 项目名
     */
    private String name;

    /**
     * 凭证id
     */
    private Integer easyCertificateId;

}
