package com.ldg.study.springCloud.service.order.support;

import com.ldg.study.springCloud.service.order.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@Service
public class OrderSupport implements OrderService {
    @Resource
    private RestTemplate restTemplate;

    /**
     * 注：使用 “fallbackMethod” 时，fallbackMethod 的方法参数个数、类型要相同
     *
     * @param orderId
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "userFallback")
    public String getBuyer(String orderId) {
        return restTemplate.getForObject("http://SERVICE-USER/user/get", String.class);
    }


    public String userFallback(String orderId) {
        return "user fallback error";
    }
}
