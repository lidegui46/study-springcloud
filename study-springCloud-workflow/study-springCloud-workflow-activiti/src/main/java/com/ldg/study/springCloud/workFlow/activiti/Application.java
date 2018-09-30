package com.ldg.study.springCloud.workFlow.activiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@SpringCloudApplication
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
