package com.easy.devops.api.domain.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增项目
 *
 * @author liupenghao
 */
@Data
public class AddProjectRequestVo {

    /**
     * 环境id
     */
    private Integer envId;

    /**
     * 项目名
     */
    @NotBlank
    private String name;

    /**
     * 仓库id
     */
    @NotNull
    private Integer easyRepositoryId;

    /**
     * 分支名
     */
    @NotBlank
    private String branch;

}
