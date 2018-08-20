package com.ldg.study.springCloud.feign.client.controller;

import com.ldg.study.springCloud.feign.feign.feign.HystrixFacllbackFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@RestController
@RequestMapping(value = "/order")
public class FeignController {

    @Resource
    private HystrixFacllbackFeign hystrixFacllbackFeign;

    /**
     * http://localhost:1145/order/getFeign
     * @return
     */
    @GetMapping(value = "getFeign")
    @HystrixCommand(fallbackMethod = "aaa")
    public String getFeign() {
        return hystrixFacllbackFeign.hello("dd");
    }

    public String aaa(){
        return "error feign fallback";
    }
}
