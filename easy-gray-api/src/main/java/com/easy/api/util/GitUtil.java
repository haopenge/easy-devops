package com.easy.api.util;


import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * git 分支操作类
 */
public class GitUtil {

    private GitUtil(){

    }

    public static final String BRANCH_PREFIX = "refs/heads/";

    /**
     * 下载指定版本的分支到本地
     */
    public static void download(CredentialsProvider credentialsProvider, String uri, String branch, String projectPath) throws GitAPIException {
        Git.cloneRepository()
                .setURI(uri)
                .setBranch(branch)
                .setDirectory(new File(projectPath))
                .setCredentialsProvider(credentialsProvider)
                .call();
    }

    /**
     * 获取git项目远程分支列表
     */
    public static List<String> getRemoteBranches(CredentialsProvider credentialsProvider, String url) throws GitAPIException {
        Collection<Ref> refList = Git.lsRemoteRepository().setRemote(url).setCredentialsProvider(credentialsProvider).call();
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
     *  根据账号密码--创建凭证
     *
     */
    public static CredentialsProvider createPwdCredential(String userName, String password) {
        return new UsernamePasswordCredentialsProvider(userName, password);
    }

    /**
     * 根据本地隐藏文件夹.git 获取仓库model
     */
    public static Repository getRepositoryFromDir(String dir) throws IOException {
        return new FileRepositoryBuilder()
                .setGitDir(Paths.get(dir, ".git").toFile())
                .build();
    }

    /**
     * 获取提交日志
     */
    public static List<String> getCommitLog(Repository repository, String branch, String commit) throws IOException {
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
