package com.easy.devops.api.domain.vo.response;

import lombok.Data;

import java.util.Date;

/**
 * git 项目获取vo, 因返回字段过多，这里仅接受我们感兴趣的；
 *
 * @author liupenghao
 */
@Data
public class GitProjectResponseVo {

    private Integer id;

    private Date createTime;

    private Date updateTime;

    private String name;

    private String subProjectPath;

    private String description;

    private String branch;

    private String cloneUrl;

    private Integer status;
}
