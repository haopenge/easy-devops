package com.easy.api.config;

import com.easy.api.service.GiteeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitConfiguration {

    @Value("${git.gitee.username:}")
    private String username;

    @Value("${git.gitee.password:}")
    private String password;

    @Value("${git.gitee.repository_project_find_url:}")
    private String repositoryProjectFindUrl;

    @Value("${git.gitee.single_project_find_url:}")
    private String singleProjectFindUrl;

    @Bean
    public GiteeService IGitService(){
        return new GiteeService(username,password,repositoryProjectFindUrl,singleProjectFindUrl);
    }

}
