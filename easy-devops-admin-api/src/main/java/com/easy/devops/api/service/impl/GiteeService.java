package com.easy.devops.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.easy.devops.api.domain.vo.GiteeProjectVo;
import com.easy.devops.api.domain.vo.response.GitProjectResponseVo;
import com.easy.devops.api.service.IGitService;
import com.easy.devops.api.util.EasyHttp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * gitee 仓库服务
 *
 * @author liuph
 */
public class GiteeService implements IGitService {

    /**
     * 仓库项目列表
     */
    private static final String FIND_REPOSITORIES_URL = "https://gitee.com/api/v5/user/repos?access_token=%s";

    /**
     * 获取单个仓库url
     */
    private static final String FIND_REPOSITORY_URL = "https://gitee.com/api/v5/repos/%s/%s?access_token=%s";

    /**
     * 获取分支url
     */
    private static final String FIND_BRANCH_URL = "https://gitee.com/api/v5/repos/%s/%s/branches?access_token=%s";

    @Override
    public List<GitProjectResponseVo> findRepositories(String accessToken) {
        List<GiteeProjectVo> giteeProjectVos = EasyHttp.httpGetArray(String.format(FIND_REPOSITORIES_URL, accessToken), null, GiteeProjectVo.class);

        List<GitProjectResponseVo> returnVoList = new ArrayList<>();
        for (GiteeProjectVo loopVo : giteeProjectVos) {
            GitProjectResponseVo gitProjectVo = new GitProjectResponseVo();
            BeanUtil.copyProperties(loopVo, gitProjectVo);
            returnVoList.add(gitProjectVo);
        }
        return returnVoList;
    }

    @Override
    public GitProjectResponseVo findRepository(String username, String repositoryName, String accessToken) {
        GiteeProjectVo giteeProjectVo = EasyHttp.httpGet(String.format(FIND_REPOSITORY_URL, username, repositoryName, accessToken), null, GiteeProjectVo.class);
        if (Objects.isNull(giteeProjectVo)) {
            return null;
        }
        GitProjectResponseVo returnVo = new GitProjectResponseVo();
        BeanUtil.copyProperties(giteeProjectVo, returnVo);
        return returnVo;
    }

    @Override
    public List<String> getRemoteBranches(String username, String repositoryName, String accessToken) {
        List<JSONObject> branches = EasyHttp.httpGetArray(String.format(FIND_BRANCH_URL, username, repositoryName, accessToken), null, JSONObject.class);
        List<String> dataList = new ArrayList<>();
        for (JSONObject loopBranch : branches) {
            dataList.add(loopBranch.getString("name"));
        }
        return dataList;
    }
}
