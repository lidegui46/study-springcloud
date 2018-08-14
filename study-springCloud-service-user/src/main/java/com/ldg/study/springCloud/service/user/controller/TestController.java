package com.ldg.study.springCloud.service.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@RestController
@RequestMapping(value = "/")
public class TestController {
    @GetMapping(value = "get")
    public String get(){
        System.out.println("11");
        return "1";
    }
}
