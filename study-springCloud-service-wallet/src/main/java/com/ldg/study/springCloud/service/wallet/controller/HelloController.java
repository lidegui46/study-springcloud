package com.ldg.study.springCloud.service.wallet.controller;

        import com.ldg.study.springCloud.feign.wallet.feign.WalletFeign;
        import org.springframework.web.bind.annotation.RestController;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@RestController
public class HelloController implements WalletFeign {
    @Override
    public String hello() {
        return "feign consumer call finished!!!";
    }
}
