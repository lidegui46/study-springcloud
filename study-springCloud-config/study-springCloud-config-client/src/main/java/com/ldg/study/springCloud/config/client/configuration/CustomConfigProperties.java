package com.ldg.study.springCloud.config.client.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author： ldg
 * @create date： 2018/8/15
 */
@Getter
@Component
@ConfigurationProperties(prefix = "spring")
public class CustomConfigProperties {
    private final Custom custom = new Custom();

    @Getter
    @Setter
    public static class Custom {
        @Value("${name}")
        private String name;
    }
}