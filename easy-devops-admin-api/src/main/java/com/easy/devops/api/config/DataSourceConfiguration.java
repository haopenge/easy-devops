package com.easy.devops.api.config;

import com.easy.devops.api.config.properties.GlobalProperties;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.nologging.NoLoggingImpl;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * 数据源配置
 *
 * @author liuph
 */
@Configuration
public class DataSourceConfiguration {

    @Resource
    private GlobalProperties globalProperties;

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Autowired DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        // 插件配置----------------start--------------------------
        // 分页插件
        System.setProperty("pagehelper.banner", "false");
        Properties properties = new Properties();
        // 因采用多数据源配置，可能存在mysql、h2、postgres、oracle等并存情况;  dialect采用默认形式，自动获取
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("reasonable", "false");
        properties.setProperty("count", "countSql");
        properties.setProperty("params", "pageNum=pageNum;pageSize=pageSize");
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(properties);

        sqlSessionFactoryBean.setPlugins(pageInterceptor);
        // 插件配置----------------end--------------------------

        // 控制台日志配置
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLogImpl(globalProperties.isSqlLogEnabled() ? StdOutImpl.class : NoLoggingImpl.class);
        sqlSessionFactoryBean.setConfiguration(configuration);

        return sqlSessionFactoryBean;
    }

}
