package com.ldg.study.springCloud.workFlow.activiti.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.ldg.study.springCloud.workFlow.activiti.configuration.properties.ActivitiConfigProperties;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Activiti工作流配置
 *
 * @author： ldg
 * @create date： 2018/9/30
 */
@Configuration
public class ActivitiConfiguration {
    @Resource
    PlatformTransactionManager transactionManager;

    @Resource
    DruidDataSource druidDataSource;

    @Resource
    private ActivitiConfigProperties activitiConfigProperties;

    @Bean
    public SpringProcessEngineConfiguration getProcessEngineConfiguration() {
        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
        config.setDataSource(druidDataSource);
        config.setTransactionManager(transactionManager);
        config.setDatabaseType("mysql");
        //自动表(下次启动时，不会删除表或数据)
        config.setDatabaseSchemaUpdate("true");
        return config;
    }
}
