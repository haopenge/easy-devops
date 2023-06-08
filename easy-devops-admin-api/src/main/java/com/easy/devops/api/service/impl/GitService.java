package com.easy.devops.api.service.impl;

import com.easy.devops.api.domain.enumx.AdminApiFailureEnum;
import com.easy.devops.api.domain.vo.GitCommitVo;
import com.easy.devops.api.exception.AdminApiException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * git通用服务
 *
 * @author liuph
 */
@Slf4j
public class GitService {

    public static final String BRANCH_PREFIX = "refs/heads/";


    /**
     * 获取git项目远程分支列表
     */
    public List<String> getRemoteBranches(CredentialsProvider credentialsProvider, String url) {
        Collection<Ref> refList = null;
        try {
            refList = Git.lsRemoteRepository().setRemote(url).setCredentialsProvider(credentialsProvider).call();
        } catch (GitAPIException e) {
            throw new AdminApiException(AdminApiFailureEnum.GIT_FETCH_EXCEPTION);
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


    public void download(CredentialsProvider credentialsProvider, String uri, String branch, String projectPath) {
        try {
            Git.cloneRepository().setURI(uri).setBranch(branch).setDirectory(new File(projectPath)).setCredentialsProvider(credentialsProvider).call();
        } catch (GitAPIException e) {
            throw new AdminApiException(AdminApiFailureEnum.GIT_FETCH_EXCEPTION);
        }
    }

    /**
     * 获取提交日志
     */
    public GitCommitVo getCommitLog(String projectDirPath, String branch) {
        Repository repository = null;
        try {
            repository = new FileRepositoryBuilder().setGitDir(Paths.get(projectDirPath, ".git").toFile()).build();
        } catch (IOException e) {
            return null;
        }

        if (branch == null) {
            try {
                branch = repository.getBranch();
            } catch (IOException e) {
                return null;
            }
        }
        Ref head = null;
        GitCommitVo gitCommitVo = null;
        try {
            head = repository.findRef(BRANCH_PREFIX + branch);
            if (head != null) {
                try (RevWalk revWalk = new RevWalk(repository)) {
                    revWalk.markStart(revWalk.parseCommit(head.getObjectId()));
                    for (RevCommit revCommit : revWalk) {
                        gitCommitVo = new GitCommitVo(revCommit.getId().getName(), revCommit.getFullMessage());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            log.warn("获取日志信息失败, name : {} , branch : {}", repository.getRemoteNames(), branch, e);
        }
        return gitCommitVo;
    }

    public static GitCommitVo getCommitLog1(String projectDirPath, String branch) {
        Repository repository = null;
        try {
            repository = new FileRepositoryBuilder().setGitDir(Paths.get(projectDirPath, ".git").toFile()).build();
        } catch (IOException e) {
            return null;
        }

        if (branch == null) {
            try {
                branch = repository.getBranch();
            } catch (IOException e) {
                return null;
            }
        }
        Ref head = null;
        GitCommitVo gitCommitVo = null;
        try {
            head = repository.findRef(BRANCH_PREFIX + branch);
            if (head != null) {
                try (RevWalk revWalk = new RevWalk(repository)) {
                    revWalk.markStart(revWalk.parseCommit(head.getObjectId()));
                    for (RevCommit revCommit : revWalk) {
                        gitCommitVo = new GitCommitVo(revCommit.getId().getName(), revCommit.getFullMessage());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            log.warn("获取日志信息失败, name : {} , branch : {}", repository.getRemoteNames(), branch, e);
        }
        return gitCommitVo;
    }
}
