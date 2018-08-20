package com.ldg.study.springCloud.feign.hystrix.factory.configuration;


import feign.Feign;
import feign.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ConditionalOnClass({FeignClient.class})
public class HystrixFallbackFactoryConfiguration {
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
