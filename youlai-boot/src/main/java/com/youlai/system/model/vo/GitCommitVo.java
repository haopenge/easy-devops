package com.youlai.system.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * git 提交信息
 *
 * @author liuph
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitCommitVo {

    /**
     * hash值
     */
    private String hash;

    /**
     * 提交信息
     */
    private String message;
}
