package com.ldg.study.springCloud.config.client.controller;

import com.ldg.study.springCloud.config.client.configuration.CloudConfigProperties;
import com.ldg.study.springCloud.config.client.configuration.CustomConfigProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author： ldg
 * @create date： 2018/8/15
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Resource
    private CloudConfigProperties cloudConfigProperties;

    @Resource
    private CustomConfigProperties customConfigProperties;

    @GetMapping("get")
    public void get() {
        printCloudConfig();
        printCustomConfig();
    }

    private void printCloudConfig(){
        CloudConfigProperties.Config config = cloudConfigProperties.getConfig();

        StringBuilder sb = new StringBuilder();
        sb.append("cloud config :")
                .append("\r\n    name :" + config.getName())
                .append("\r\n    name :" + config.getUri())
                .append("\r\n    name :" + config.getProfile());
        System.out.println(sb.toString());
    }

    private void printCustomConfig(){
        CustomConfigProperties.Custom custom = customConfigProperties.getCustom();

        StringBuilder sb = new StringBuilder();
        sb.append("custom config :")
                .append("\r\n    name :" + custom.getName());
        System.out.println(sb.toString());
    }
}
