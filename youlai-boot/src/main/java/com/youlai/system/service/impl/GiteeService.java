package com.youlai.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.youlai.system.common.util.EasyHttp;
import com.youlai.system.model.vo.GiteeProjectVo;
import com.youlai.system.model.vo.response.GitCertificateResponseVo;
import com.youlai.system.service.IGitService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * gitee 仓库服务
 *
 * @author liuph
 */
@Service
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
    public List<GitCertificateResponseVo> findRepositories(String accessToken) {
        List<GiteeProjectVo> giteeProjectVos = EasyHttp.httpGetArray(String.format(FIND_REPOSITORIES_URL, accessToken), null, GiteeProjectVo.class);
        return BeanUtil.copyToList(giteeProjectVos, GitCertificateResponseVo.class);
    }

    @Override
    public GitCertificateResponseVo findRepository(String username, String repositoryName, String accessToken) {
        GiteeProjectVo giteeProjectVo = EasyHttp.httpGet(String.format(FIND_REPOSITORY_URL, username, repositoryName, accessToken), null, GiteeProjectVo.class);
        if (Objects.isNull(giteeProjectVo)) {
            return null;
        }
        GitCertificateResponseVo returnVo = new GitCertificateResponseVo();
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
