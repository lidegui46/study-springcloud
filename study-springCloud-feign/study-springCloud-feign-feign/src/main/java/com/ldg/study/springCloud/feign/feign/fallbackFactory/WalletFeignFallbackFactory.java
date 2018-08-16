package com.ldg.study.springCloud.feign.feign.fallbackFactory;

import com.ldg.study.springCloud.feign.feign.service.WalletFeign;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 服务降级
 *
 * @author： ldg
 * @create date： 2018/8/16
 */
@Component
public class WalletFeignFallbackFactory implements FallbackFactory<WalletFeign> {
    @Override
    public WalletFeign create(Throwable throwable) {
        return new WalletFeign() {
            @Override
            public String hello(String code) {
                return "该接口已被服务降级";
            }
        };
    }
}
