package com.easy.devops.api.service.impl;

import com.easy.devops.api.domain.vo.response.GitProjectResponseVo;
import com.easy.devops.api.service.IGitService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * gitlab 仓库服务
 *
 * @author liuph
 */
@Service
public class GitlabService implements IGitService {
    @Override
    public List<GitProjectResponseVo> findRepositories(String accessToken) {
        return null;
    }

    @Override
    public GitProjectResponseVo findRepository(String username, String repositoryName, String accessToken) {
        return null;
    }

    @Override
    public List<String> getRemoteBranches(String username, String repositoryName, String accessToken) {
        return null;
    }
}
