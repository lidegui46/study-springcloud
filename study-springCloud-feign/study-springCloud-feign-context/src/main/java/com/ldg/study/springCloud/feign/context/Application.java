package com.ldg.study.springCloud.feign.context;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Application {
    public static void main(String[] args) { SpringApplication.run(Application.class, args); }
}
