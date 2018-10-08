package com.ldg.study.springCloud.workFlow.activiti.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wenjie
 * @date 2017/12/5 0005 15:24
 */
@Getter
@Component
@ConfigurationProperties(prefix = "spring")
public final class ActivitiConfigProperties {
    private Activiti activiti = new Activiti();

    private Datasource datasource = new Datasource();

    @Setter
    @Getter
    public static final class Activiti {
        @Value("${database-schema-update}")
        private String databaseSchemaUpdate;
        @Value("${database-schema}")
        private String databaseSchema;
        @Value("${database-type}")
        private String databaseType;
        @Value("${check-process-definitions}")
        private String checkProcessDefinitions;
    }

    @Getter
    @Setter
    public static final class Datasource{
        @Value("${driver-class-name}")
        private String driverClassName;
        @Value("${url}")
        private String url;
        @Value("${username}")
        private String username;
        @Value("${password}")
        private String password;
        @Value("${filters}")
        private String filters;
        @Value("${maxActive}")
        private Integer maxActive;
        @Value("${initialSize}")
        private Integer initialSize;
        @Value("${maxWait}")
        private Integer maxWait;
        @Value("${minIdle}")
        private Integer minIdle;
        @Value("${timeBetweenEvictionRunsMillis}")
        private Integer timeBetweenEvictionRunsMillis;
        @Value("${minEvictableIdleTimeMillis}")
        private Integer minEvictableIdleTimeMillis;
        @Value("${validationQuery}")
        private String validationQuery;
        @Value("${testWhileIdle}")
        private Boolean testWhileIdle;
        @Value("${testOnBorrow}")
        private Boolean testOnBorrow;
        @Value("${testOnReturn}")
        private Boolean testOnReturn;
        @Value("${maxOpenPreparedStatements}")
        private Integer maxOpenPreparedStatements;
        @Value("${removeAbandoned}")
        private Boolean removeAbandoned;
        @Value("${removeAbandonedTimeout}")
        private Integer removeAbandonedTimeout;
        @Value("${logAbandoned}")
        private Boolean logAbandoned;
        @Value("${poolPreparedStatements}")
        private Boolean poolPreparedStatements;
        @Value("${maxPoolPreparedStatementPerConnectionSize}")
        private Integer maxPoolPreparedStatementPerConnectionSize;
        @Value("${initSql}")
        private List<String> initSql;
    }
}
