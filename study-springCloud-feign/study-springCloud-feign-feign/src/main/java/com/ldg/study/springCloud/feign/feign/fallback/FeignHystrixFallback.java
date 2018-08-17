package com.ldg.study.springCloud.feign.feign.fallback;

import com.ldg.study.springCloud.feign.feign.feign.HystrixFacllbackFeign;
import org.springframework.stereotype.Component;

/**
 * 服务降级
 *
 * @author： ldg
 * @create date： 2018/8/16
 */
@Component
public class FeignHystrixFallback implements HystrixFacllbackFeign {

    @Override
    public String hello(String code) {
        return "error";
    }
}
