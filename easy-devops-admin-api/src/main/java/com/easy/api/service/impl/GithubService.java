package com.easy.api.service.impl;

import com.easy.api.domain.vo.GithubProjectVo;
import com.easy.api.domain.vo.response.GitProjectResponseVo;
import com.easy.api.service.AbstractGitService;
import com.easy.api.util.EasyHttp;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class GithubService extends AbstractGitService {

    private final String password;

    private final String repositoryProjectFindUrl;

    public GithubService(String username,String password,String repositoryProjectFindUrl) {
        setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
        this.password = password;
        this.repositoryProjectFindUrl = repositoryProjectFindUrl;
    }

    @Override
    public List<GitProjectResponseVo> findRepositoryProject() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "application/vnd.github+json");
        headerMap.put("Authorization", "Bearer " + password);
        List<GithubProjectVo> voList = EasyHttp.httpGetArray(repositoryProjectFindUrl, headerMap, GithubProjectVo.class);

        List<GitProjectResponseVo> returnVoList = new ArrayList<>();
        for (GithubProjectVo loopVo : voList) {
            GitProjectResponseVo gitProjectVo = new GitProjectResponseVo();
            BeanUtils.copyProperties(loopVo, gitProjectVo);
            returnVoList.add(gitProjectVo);
        }
        return returnVoList;
    }

    @Override
    public GitProjectResponseVo findRepositoryByFullName(String fullName) {
        return null;
    }

}
