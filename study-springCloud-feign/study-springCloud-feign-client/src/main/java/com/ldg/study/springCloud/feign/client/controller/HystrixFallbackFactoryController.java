//package com.ldg.study.springCloud.feign.client.controller;
//
//import com.ldg.study.springCloud.feign.hystrix.factory.feign.HystrixFactoryFeign;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author： ldg
// * @create date： 2018/8/14
// */
//@RestController
//@RequestMapping(value = "/order")
//public class HystrixFallbackFactoryController {
//
//    @Autowired
//    private HystrixFactoryFeign hystrixFactoryFeign;
//
//    /**
//     * http://localhost:1145/order/getFeign
//     * @return
//     */
//    @GetMapping(value = "getFeignFactory")
//    public String getFeignFactory() {
//        return userFeignHystrixFactoryClient.findById(22L);
//    }
//}
