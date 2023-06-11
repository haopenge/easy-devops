package com.easy.devops.api.domain.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 编辑项目Vo
 *
 * @author liupenghao
 */
@Data
public class EditProjectRequestVo {

    private Integer id;

    /**
     * 环境id
     */
    private Integer envId;

    /**
     * 仓库id
     */
    private Integer easyRepositoryId;

    /**
     * 分支名
     */
    private String branch;
}
