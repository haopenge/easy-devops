package com.easy.api.service;

import com.easy.api.domain.enumx.FailureEnum;
import com.easy.api.domain.vo.GithubProjectVo;
import com.easy.api.domain.vo.response.GitProjectResponseVo;
import com.easy.api.exception.ServiceException;
import com.easy.api.util.EasyHttp;
import lombok.Data;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;


@Data
public class GithubService {

    public static final String BRANCH_PREFIX = "refs/heads/";

    private CredentialsProvider credentialsProvider;

    private String githubPassword;

    private String githubRepositoryProjectFindUrl;

    public GithubService(CredentialsProvider credentialsProvider, String githubPassword, String githubRepositoryProjectFindUrl) {
        this.credentialsProvider = credentialsProvider;
        this.githubPassword = githubPassword;
        this.githubRepositoryProjectFindUrl = githubRepositoryProjectFindUrl;
    }

    public List<GitProjectResponseVo> findRepositoryProject() {
        List<GitProjectResponseVo> returnVoList = new ArrayList<>();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "application/vnd.github+json");
        headerMap.put("Authorization", "Bearer " + githubPassword);
        List<GithubProjectVo> voList = EasyHttp.httpGetArray(githubRepositoryProjectFindUrl, headerMap, GithubProjectVo.class);

        for (GithubProjectVo loopVo : voList) {
            GitProjectResponseVo gitProjectVo = new GitProjectResponseVo();
            BeanUtils.copyProperties(loopVo, gitProjectVo);
            returnVoList.add(gitProjectVo);
        }
        return returnVoList;
    }

    /**
     * 下载指定版本的分支到本地
     */
    public void download(String uri, String branch, String projectPath){
        try {
            Git.cloneRepository()
                    .setURI(uri)
                    .setBranch(branch)
                    .setDirectory(new File(projectPath))
                    .setCredentialsProvider(credentialsProvider)
                    .call();
        } catch (GitAPIException e) {
            throw new ServiceException(FailureEnum.GIT_FETCH_EXCEPTION);
        }
    }

    /**
     * 获取git项目远程分支列表
     */
    public List<String> getRemoteBranches(String url) {
        Collection<Ref> refList = null;
        try {
            refList = Git.lsRemoteRepository().setRemote(url).setCredentialsProvider(credentialsProvider).call();
        } catch (GitAPIException e) {
            throw new ServiceException(FailureEnum.GIT_FETCH_EXCEPTION);
        }
        List<String> branchList = new ArrayList<>();
        for (Ref ref : refList) {
            String refName = ref.getName();
            if (refName.startsWith(BRANCH_PREFIX)) {
                String branchName = refName.replace(BRANCH_PREFIX, "");
                branchList.add(branchName);
            }
        }
        return branchList;
    }

    /**
     * 根据本地隐藏文件夹.git 获取仓库model
     */
    public Repository getRepositoryFromDir(String dir) throws IOException {
        return new FileRepositoryBuilder()
                .setGitDir(Paths.get(dir, ".git").toFile())
                .build();
    }

    /**
     * 获取提交日志
     */
    public List<String> getCommitLog(Repository repository, String branch, String commit) throws IOException {
        if (branch == null) {
            branch = repository.getBranch();
        }
        Ref head = repository.findRef(BRANCH_PREFIX + branch);
        List<String> commits = new ArrayList<>();
        if (head != null) {
            try (RevWalk revWalk = new RevWalk(repository)) {
                revWalk.markStart(revWalk.parseCommit(head.getObjectId()));
                for (RevCommit revCommit : revWalk) {
                    if (revCommit.getId().getName().equals(commit)) {
                        break;
                    }
                    commits.add(revCommit.getFullMessage());
                }
                revWalk.dispose();
            }
        }
        return commits;
    }

}
