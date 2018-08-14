package com.ldg.study.springCloud.feign.client;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@SpringCloudApplication
@EnableDiscoveryClient
@EnableFeignClients({"com.ldg.study.springCloud.feign.feign"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
