package com.ldg.study.springCloud.ribbon.client.controller;

import com.ldg.study.springCloud.ribbon.client.service.OrderService;
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

    /**
     * http://localhost:1120/order/get
     * @return
     */
    @GetMapping(value = "get")
    public String get() {
        return orderService.getBuyer("asd");
    }
}
