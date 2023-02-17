package com.easy.api.service;

import com.easy.api.domain.vo.response.GitProjectResponseVo;
import org.eclipse.jgit.lib.Repository;

import java.util.List;

/**
 * git 仓库信息获取服务
 * @author liuph
 */
public interface IGitService {
    /**
     * 获取git 所有项目
     * @return 项目信息
     */
    List<GitProjectResponseVo> findRepositoryProject();

    /**
     * 获取git项目远程分支列表
     */
    List<String> getRemoteBranches(String url);

    /**
     * @desc 下载指定版本的分支到本地
     * @author liuph
     * @date  2023/02/16 14:45
     * @param uri           项目路径
     * @param branch        项目分支
     * @param projectPath   本地存储路径
     */
    void download(String uri, String branch, String projectPath);

    /**
     * 获取提交日志
     * @param repository    仓库
     * @param branch        分支
     * @param commit        上次日志commit
     * @return 提交日志
     */
    List<String> getCommitLog(Repository repository, String branch, String commit);
}
