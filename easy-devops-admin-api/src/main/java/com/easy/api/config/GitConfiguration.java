package com.easy.api.config;

import com.easy.api.config.properties.GitProperties;
import com.easy.api.service.impl.GiteeService;
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
