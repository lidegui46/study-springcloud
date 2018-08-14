package com.ldg.study.springCloud.service.order.controller;

import com.ldg.study.springCloud.service.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "get")
    public String get() {
        return orderService.getBuyer("asd");
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
