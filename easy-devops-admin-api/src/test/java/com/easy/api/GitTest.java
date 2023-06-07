package com.easy.api;

import com.easy.api.domain.enumx.AdminApiFailureEnum;
import com.easy.api.exception.AdminApiException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

/**
 * @author liuph
 * @desc
 * @date 2022/11/14 00:00
 */
public class GitTest {

    public static void download(String uri, String branch, String projectPath){
        UsernamePasswordCredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider("xiaoyuxxx", "7f6746302ae118a3002cc3679f83329d");
        try {
            Git.cloneRepository()
                    .setURI(uri)
                    .setBranch(branch)
                    .setDirectory(new File(projectPath))
                    .setCredentialsProvider(credentialsProvider)
                    .call();
        } catch (GitAPIException e) {
            throw new AdminApiException(AdminApiFailureEnum.GIT_FETCH_EXCEPTION);
        }

    }
    public static void main(String[] args) {
        download("https://gitee.com/xiaoyuxxx/easy-gray.git","feature/k8s-client-deploy","/Users/liupenghao/Desktop/test/easy-gray");
    }
}
