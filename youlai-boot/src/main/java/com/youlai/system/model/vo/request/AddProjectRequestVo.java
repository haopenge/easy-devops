package com.youlai.system.model.vo.request;

import lombok.Data;

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
    private String name;

    /**
     * 仓库id
     */
    private Integer easyRepositoryId;


    private String branch;

}
