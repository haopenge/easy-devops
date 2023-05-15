package com.easy.api.service;

import com.easy.api.domain.vo.response.GitProjectResponseVo;

import java.util.List;

/**
 * git 仓库信息获取服务
 *
 * @author liuph
 */
public interface IGitService {
    /**
     * 获取git 所有项目
     *
     * @return 项目信息
     */
    List<GitProjectResponseVo> findRepositoryProject();

    /**
     * 获取git 单个项目
     *
     * @return 项目信息
     */
    GitProjectResponseVo findRepositoryByFullName(String fullName);

    /**
     * 获取git项目远程分支列表
     */
    List<String> getRemoteBranches(String url);

    /**
     * @param uri         项目路径
     * @param branch      项目分支
     * @param projectPath 本地存储路径
     * @desc 下载指定版本的分支到本地
     * @author liuph
     * @date 2023/02/16 14:45
     */
    void download(String uri, String branch, String projectPath);

    /**
     * 获取提交日志
     *
     * @param projectDirPath 本地仓库地址
     * @param branch         分支
     * @param commit         上次日志commit
     * @return 提交日志
     */
    List<String> getCommitLog(String projectDirPath, String branch, String commit);
}
