package com.ldg.study.springCloud.feign.feign.feign;

import com.ldg.study.springCloud.feign.feign.fallback.FeignHystrixFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@FeignClient(name = "feign-context"
        //, configuration = BaseFeignConfiguration.class
        , fallback = FeignHystrixFallback.class
)
public interface HystrixFacllbackFeign {
    @GetMapping("/hello")
    //@RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam("code") String code);
}