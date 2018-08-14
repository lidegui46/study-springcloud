package com.ldg.study.springCloud.feign.wallet.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@FeignClient("service-wallet")
public interface WalletFeign {
    @RequestMapping("/hello")
    public String hello();
}
