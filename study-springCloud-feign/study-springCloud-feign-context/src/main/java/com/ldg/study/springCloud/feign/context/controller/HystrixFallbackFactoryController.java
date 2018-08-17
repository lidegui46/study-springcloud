package com.ldg.study.springCloud.feign.context.controller;

import com.ldg.study.springCloud.feign.hystrix.factory.feign.HystrixFactoryFeign;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@RestController
public class HystrixFallbackFactoryController implements HystrixFactoryFeign {
    @Override
    public String findById(Long id) {
        return "test";
    }
}
