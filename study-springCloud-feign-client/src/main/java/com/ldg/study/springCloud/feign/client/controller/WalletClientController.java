package com.ldg.study.springCloud.feign.client.controller;

import com.ldg.study.springCloud.feign.feign.WalletFeign;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@RestController
@RequestMapping(value = "/order")
public class WalletClientController {

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private WalletFeign walletFeign;

    /**
     * http://localhost:11450/order/getFeign
     * @return
     */
    @GetMapping(value = "getFeign")
    public String getFeign() {
        return walletFeign.hello("dd");
    }

    /**
     * 查看负载均衡信息
     */
    private void getDiscovery() {
        List<ServiceInstance> instances = discoveryClient.getInstances("service-user");
        instances.forEach(p -> {
            System.out.println(p.getHost() + "   " + p.getServiceId());
        });
    }
}
