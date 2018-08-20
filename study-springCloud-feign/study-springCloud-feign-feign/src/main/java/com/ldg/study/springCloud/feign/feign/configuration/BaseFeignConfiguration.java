package com.ldg.study.springCloud.feign.feign.configuration;


import feign.Feign;
import feign.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConditionalOnClass({FeignClient.class})
public class BaseFeignConfiguration {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }
}
