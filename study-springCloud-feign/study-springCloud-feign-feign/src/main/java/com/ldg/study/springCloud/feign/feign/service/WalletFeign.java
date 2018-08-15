package com.ldg.study.springCloud.feign.feign.service;

import com.ldg.study.springCloud.feign.feign.configuration.BaseFeignConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@FeignClient(name = "feign-context", configuration = BaseFeignConfiguration.class)
public interface WalletFeign {
    @GetMapping("/hello")
    public String hello(@RequestParam("code") String code);
}
