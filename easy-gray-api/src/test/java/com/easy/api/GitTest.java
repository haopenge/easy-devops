package com.easy.api;

import com.easy.api.domain.enumx.FailureEnum;
import com.easy.api.exception.ServiceException;
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
        UsernamePasswordCredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider("anhusky@163.com", "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");
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
    public static void main(String[] args) {
        download("https://github.com/haopenge/easy-gray.git","main","/Users/liupenghao/Desktop/test/easy-gray/easy-gray");
    }
}
