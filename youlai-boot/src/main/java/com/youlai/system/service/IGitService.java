package com.youlai.system.service;


import com.youlai.system.model.vo.response.GitCertificateResponseVo;

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
     * @param accessToken 认证token
     * @return 项目信息
     */
    List<GitCertificateResponseVo> findRepositories(String accessToken);

    /**
     * 获取git 单个项目
     *
     * @param username       用户名
     * @param repositoryName 仓库名
     * @param accessToken    认证token
     * @return 项目信息
     */
    GitCertificateResponseVo findRepository(String username, String repositoryName, String accessToken);

    /**
     * 获取git仓库远程分支列表
     *
     * @param username       用户名
     * @param repositoryName 仓库名
     * @param accessToken    认证token
     */
    List<String> getRemoteBranches(String username, String repositoryName, String accessToken);

}
