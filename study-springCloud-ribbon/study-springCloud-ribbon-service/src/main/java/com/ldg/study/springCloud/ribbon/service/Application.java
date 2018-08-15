package com.ldg.study.springCloud.ribbon.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 服务注册 与 发现
 *
 * @author： ldg
 * @create date： 2018/8/13
 */
//@EnableEurekaClient         //和EnableDiscoveryClient相似
@EnableDiscoveryClient  //和EnableEurekaClient相似
@SpringBootApplication //(scanBasePackages = "com.ldg.study.springCloud")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
