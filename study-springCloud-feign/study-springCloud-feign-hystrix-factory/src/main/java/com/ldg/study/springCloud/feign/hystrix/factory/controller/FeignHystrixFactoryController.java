package com.ldg.study.springCloud.feign.hystrix.factory.controller;


import com.ldg.study.springCloud.feign.hystrix.factory.feign.HystrixFactoryFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class FeignHystrixFactoryController {

    @Resource
    private HystrixFactoryFeign hystrixFactoryFeign;

    @GetMapping("/movie/{id}")
    public String findById(@PathVariable Long id) {
        System.out.println("======== findById Controller " + Thread.currentThread().getThreadGroup() + " - " + Thread.currentThread().getId() + " - " + Thread.currentThread().getName());
        return hystrixFactoryFeign.findById(id);
    }
}
