package com.easy.api.config;

import com.easy.api.service.GithubService;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitConfiguration {

    @Value("${git.github.username:}")
    private String githubUsername;

    @Value("${git.github.password:}")
    private String githubPassword;

    @Value("${git.github.repository_project_find_url:https://api.github.com/user/repos?sort=updated&direction=desc}")
    private String githubRepositoryProjectFindUrl;

    @Bean
    public CredentialsProvider credentialsProvider() {
       return new UsernamePasswordCredentialsProvider(githubUsername, githubPassword);
    }

    @Bean
    public GithubService githubService(CredentialsProvider credentialsProvider) {
        return new GithubService(credentialsProvider, githubPassword,githubRepositoryProjectFindUrl);
    }

}
