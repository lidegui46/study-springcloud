package com.ldg.study.springCloud.feign.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@SpringBootApplication(scanBasePackages = {"com.ldg.study.springCloud.feign.feign"})
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
