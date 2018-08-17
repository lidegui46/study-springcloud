package com.ldg.study.springCloud.feign.feign.controller;


import com.ldg.study.springCloud.feign.feign.feign.HystrixFacllbackFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class FeignHystrixController {

    @Resource
    private HystrixFacllbackFeign hystrixFacllbackFeign;

    @GetMapping("/findId")
    public String findById(@RequestParam("id") String id) {
        System.out.println("======== findById Controller " + Thread.currentThread().getThreadGroup() + " - " + Thread.currentThread().getId() + " - " + Thread.currentThread().getName());
        return hystrixFacllbackFeign.hello(id);
    }
}
