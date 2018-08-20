package com.ldg.study.springCloud.feign.client.controller;

import com.ldg.study.springCloud.feign.hystrix.factory.feign.HystrixFactoryFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@RestController
@RequestMapping(value = "/order")
public class HystrixFallbackFactoryController {

    @Autowired
    private HystrixFactoryFeign hystrixFactoryFeign;

    /**
     * http://localhost:1145/order/getFeignFactory
     *
     * @return
     */
    @GetMapping(value = "getFeignFactory")
    @HystrixCommand(fallbackMethod = "aaa")
    public String getFeignFactory() {
        return hystrixFactoryFeign.findById(22L);
    }

    public String aaa() {
       return "error feign fallback factory";
    }
}
