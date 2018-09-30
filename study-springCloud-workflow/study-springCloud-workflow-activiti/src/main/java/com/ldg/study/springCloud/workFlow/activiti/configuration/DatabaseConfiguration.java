package com.ldg.study.springCloud.workFlow.activiti.configuration;

/**
 * druid 配置.
 * <p>
 * 这样的方式不需要添加注解：@ServletComponentScan
 *
 * @author wenjie
 */

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.WebStatFilter;
import com.google.common.collect.Lists;
import com.ldg.study.springCloud.workFlow.activiti.configuration.properties.ActivitiConfigProperties;
import lombok.extern.log4j.Log4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;


@Configuration
@Log4j
public class DatabaseConfiguration {

    @Resource
    private ActivitiConfigProperties cloudConfigProperties;

    /**
     * 数据库连接池配置
     */
    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource druidDataSource() {

        log.debug("Configuring Datasource");
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(cloudConfigProperties.getDatasource().getDriverClassName());
        druidDataSource.setUrl(cloudConfigProperties.getDatasource().getUrl());
        druidDataSource.setUsername(cloudConfigProperties.getDatasource().getUsername());
        druidDataSource.setPassword(cloudConfigProperties.getDatasource().getPassword());
        try {
            druidDataSource.setFilters(cloudConfigProperties.getDatasource().getFilters());
        } catch (SQLException e) {
            log.error("druid init filters error");
        }
        druidDataSource.setConnectionInitSqls(cloudConfigProperties.getDatasource().getInitSql());
        druidDataSource.setMaxActive(cloudConfigProperties.getDatasource().getMaxActive());
        druidDataSource.setInitialSize(cloudConfigProperties.getDatasource().getInitialSize());
        druidDataSource.setMaxWait(cloudConfigProperties.getDatasource().getMaxWait());
        druidDataSource.setMinIdle(cloudConfigProperties.getDatasource().getMinIdle());
        druidDataSource.setTimeBetweenEvictionRunsMillis(cloudConfigProperties.getDatasource().getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(cloudConfigProperties.getDatasource().getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(cloudConfigProperties.getDatasource().getValidationQuery());
        druidDataSource.setTestWhileIdle(cloudConfigProperties.getDatasource().getTestWhileIdle());
        druidDataSource.setTestOnBorrow(cloudConfigProperties.getDatasource().getTestOnBorrow());
        druidDataSource.setTestOnReturn(cloudConfigProperties.getDatasource().getTestOnReturn());
        druidDataSource.setMaxOpenPreparedStatements(cloudConfigProperties.getDatasource().getMaxOpenPreparedStatements());
        druidDataSource.setRemoveAbandoned(cloudConfigProperties.getDatasource().getRemoveAbandoned());
        druidDataSource.setRemoveAbandonedTimeout(cloudConfigProperties.getDatasource().getRemoveAbandonedTimeout());
        druidDataSource.setLogAbandoned(cloudConfigProperties.getDatasource().getLogAbandoned());
        druidDataSource.setPoolPreparedStatements(cloudConfigProperties.getDatasource().getPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(cloudConfigProperties.getDatasource().getMaxPoolPreparedStatementPerConnectionSize());

        // druid 日志配置
        final Slf4jLogFilter druidLogFilter = new Slf4jLogFilter();
        druidLogFilter.setConnectionLogEnabled(false);
        druidLogFilter.setResultSetLogEnabled(false);
        druidLogFilter.setStatementParameterClearLogEnable(false);

        List<Filter> druidFilterList = Lists.newArrayList();
        druidFilterList.add(druidLogFilter);

        druidDataSource.setProxyFilters(druidFilterList);

        return druidDataSource;
    }


    /**
     * 注册一个：filterRegistrationBean
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter2() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");

        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*");
        return filterRegistrationBean;
    }

}