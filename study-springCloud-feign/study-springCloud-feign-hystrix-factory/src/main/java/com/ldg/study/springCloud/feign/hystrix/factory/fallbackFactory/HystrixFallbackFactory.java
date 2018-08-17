package com.ldg.study.springCloud.feign.hystrix.factory.fallbackFactory;

import com.ldg.study.springCloud.feign.hystrix.factory.feign.HystrixFactoryFeign;
import feign.hystrix.FallbackFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Hystrix 客户端回退机制类。
 * <p>
 * 这里加上注解 Component 的目的：就是因为没有这个注解，运行时候会报错，报错会说没有该类的这个实例，所以我们就想到要实例化这个类，因此加了这个注解。
 *
 * @author hmilyylimh
 * @version 0.0.1
 * @date 2017/9/24
 */
@Component
public class HystrixFallbackFactory implements FallbackFactory<HystrixFactoryFeign> {

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(HystrixFallbackFactory.class);


    @Override
    public HystrixFactoryFeign create(Throwable e) {

        Logger.info("fallback; reason was: {}", e.getMessage());
        System.out.println("======== UserFeignHystrixFactoryClient.create " + Thread.currentThread().getThreadGroup() + " - " + Thread.currentThread().getId() + " - " + Thread.currentThread().getName());

        return new HystrixFactoryFeign() {

            @Override
            public String findById(Long id) {
                System.out.println("======== findById FallBackFactory " + Thread.currentThread().getThreadGroup() + " - " + Thread.currentThread().getId() + " - " + Thread.currentThread().getName());

                return "error tips : hystrix";
            }
        };
    }
}

/****************************************************************************************
 @FeignClient(name = "hello", fallbackFactory = HystrixClientFallbackFactory.class)
 protected interface HystrixClient {
 @RequestMapping(method = RequestMethod.GET, value = "/hello")
 Hello iFailSometimes();
 }

 @Component static class HystrixClientFallbackFactory implements FallbackFactory<HystrixClient> {
 @Override public HystrixClient create(Throwable cause) {
 return new HystrixClientWithFallBackFactory() {
 @Override public Hello iFailSometimes() {
 return new Hello("fallback; reason was: " + cause.getMessage());
 }
 };
 }
 }
 ****************************************************************************************/