package com.easy.devops.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSONObject;
import com.easy.devops.api.domain.vo.GiteeProjectVo;
import com.easy.devops.api.domain.vo.GithubProjectVo;
import com.easy.devops.api.domain.vo.response.GitCertificateResponseVo;
import com.easy.devops.api.service.IGitService;
import com.easy.devops.api.util.EasyHttp;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * github 仓库服务
 *
 * @author liuph
 */
@Service
public class GithubService implements IGitService {

    /**
     * 仓库项目列表
     */
    private static final String FIND_REPOSITORIES_URL = "https://api.github.com/user/repos";

    /**
     * 获取单个仓库url
     */
    private static final String FIND_REPOSITORY_URL = "https://api.github.com/repos/%s/%s";

    /**
     * 获取分支url
     */
    private static final String FIND_BRANCH_URL = "https://api.github.com/repos/%s/%s/branches";

    @Override
    public List<GitCertificateResponseVo> findRepositories(String accessToken) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "application/vnd.github+json");
        headerMap.put("Authorization", "Bearer " + accessToken);

        List<GithubProjectVo> githubProjectVos = EasyHttp.httpGetArray(FIND_REPOSITORIES_URL, headerMap, GithubProjectVo.class);
        return BeanUtil.copyToList(githubProjectVos, GitCertificateResponseVo.class);
    }

    @Override
    public GitCertificateResponseVo findRepository(String username, String repositoryName, String accessToken) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "application/vnd.github+json");
        headerMap.put("Authorization", "Bearer " + accessToken);

        GithubProjectVo githubProjectVo = EasyHttp.httpGet(String.format(FIND_REPOSITORY_URL, username, repositoryName), headerMap, GithubProjectVo.class);
        if (Objects.isNull(githubProjectVo)) {
            return null;
        }
        GitCertificateResponseVo returnVo = new GitCertificateResponseVo();
        BeanUtil.copyProperties(githubProjectVo, returnVo);
        return returnVo;
    }

    @Override
    public List<String> getRemoteBranches(String username, String repositoryName, String accessToken) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "application/vnd.github+json");
        headerMap.put("Authorization", "Bearer " + accessToken);

        List<JSONObject> jsonObjectList = EasyHttp.httpGetArray(String.format(FIND_BRANCH_URL, username, repositoryName), headerMap, JSONObject.class);
        if (Objects.isNull(jsonObjectList)) {
            return Collections.emptyList();
        }
        List<String> dataList = new ArrayList<>();
        for (JSONObject loopJsonObj : jsonObjectList) {
            dataList.add(loopJsonObj.getString("name"));
        }

        return dataList;
    }
}
