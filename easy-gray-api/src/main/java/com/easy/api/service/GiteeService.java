package com.easy.api.service;

import com.easy.api.domain.vo.GiteeProjectVo;
import com.easy.api.domain.vo.response.GitProjectResponseVo;
import com.easy.api.util.EasyHttp;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author liuph
 */
public class GiteeService extends AbstractGitService{

    private final String password;

    private final String repositoryProjectFindUrl;

    private final String singleProjectFindUrl;

    public GiteeService(String username,String password,String repositoryProjectFindUrl,String singleProjectFindUrl) {
        setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
        this.password = password;
        this.repositoryProjectFindUrl = repositoryProjectFindUrl;
        this.singleProjectFindUrl = singleProjectFindUrl;
    }

    @Override
    public List<GitProjectResponseVo> findRepositoryProject() {
        List<GiteeProjectVo> giteeProjectVos = EasyHttp.httpGetArray(repositoryProjectFindUrl + "?access_token=" + password, null, GiteeProjectVo.class);

        List<GitProjectResponseVo> returnVoList = new ArrayList<>();
        for (GiteeProjectVo loopVo : giteeProjectVos) {
            GitProjectResponseVo gitProjectVo = new GitProjectResponseVo();
            BeanUtils.copyProperties(loopVo, gitProjectVo);
            returnVoList.add(gitProjectVo);
        }
        return returnVoList;
    }

    @Override
    public GitProjectResponseVo findRepositoryByFullName(String fullName) {
        GiteeProjectVo giteeProjectVo = EasyHttp.httpGet(singleProjectFindUrl  + "/" + fullName  + "?access_token=" + password, null, GiteeProjectVo.class);
        if(Objects.isNull(giteeProjectVo)){
            return null;
        }
        GitProjectResponseVo returnVo = new GitProjectResponseVo();
        BeanUtils.copyProperties(giteeProjectVo, returnVo);
        return returnVo;
    }
}
