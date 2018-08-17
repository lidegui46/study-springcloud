package com.ldg.study.springCloud.feign.context.controller;

import com.ldg.study.springCloud.feign.feign.feign.HystrixFacllbackFeign;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@RestController
public class HelloController implements HystrixFacllbackFeign {
    @Override
    public String hello(String code)
    {
        System.out.println("sasd");
        return "feign consumer call finished!!!  " + code;
    }
}
