package com.easy.devops.api.config;

import com.easy.devops.api.config.properties.GitProperties;
import com.easy.devops.api.service.impl.GiteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liupenghao
 */
@Configuration
public class GitConfiguration {

    @Autowired
    private GitProperties gitProperties;

    @Bean
    public GiteeService IGitService() {
        return new GiteeService(gitProperties.getUsername(), gitProperties.getPassword(), gitProperties.getProjectFindUrl(), gitProperties.getProjectDetailFindUrl());
    }

}
