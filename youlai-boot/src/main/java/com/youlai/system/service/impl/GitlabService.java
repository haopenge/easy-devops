package com.youlai.system.service.impl;

import com.youlai.system.model.vo.response.GitCertificateResponseVo;
import com.youlai.system.service.IGitService;
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
    public List<GitCertificateResponseVo> findRepositories(String accessToken) {
        return null;
    }

    @Override
    public GitCertificateResponseVo findRepository(String username, String repositoryName, String accessToken) {
        return null;
    }

    @Override
    public List<String> getRemoteBranches(String username, String repositoryName, String accessToken) {
        return null;
    }
}
