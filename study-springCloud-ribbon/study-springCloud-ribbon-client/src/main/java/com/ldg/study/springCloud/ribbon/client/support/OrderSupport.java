package com.ldg.study.springCloud.ribbon.client.support;

import com.ldg.study.springCloud.ribbon.client.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@Service
public class OrderSupport implements OrderService {
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;

    private static final String RIBBON_SERVICE_NAME = "ribbon-service";

    /**
     * 查看负载均衡信息
     */
    private void getDiscovery() {
        List<ServiceInstance> instances = discoveryClient.getInstances(RIBBON_SERVICE_NAME);
        instances.forEach(p -> {
            System.out.println(p.getHost() + "   " + p.getServiceId());
        });
    }

    /**
     * 注：使用 “fallbackMethod” 时，fallbackMethod 的方法参数个数、类型要相同
     *
     * @param orderId
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "userFallback")
    public String getBuyer(String orderId) {
        return restTemplate.getForObject(String.format("http://%s/user/get", RIBBON_SERVICE_NAME), String.class);
    }

    public String userFallback(String orderId) {
        return "user fallback error";
    }
}
