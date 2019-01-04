package com.ldg.study.springCloud.jdk.basic.dynamicProxy.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 通用代理
 *
 * @author： ldg
 * @create date： 2019/1/4
 */
public class Cgliber implements MethodInterceptor {

    /**
     * 生成代理实例（依据被代理类来生成代理类），Enhancer则是代理类生成工具
     *
     * @param object
     * @return
     */
    public <T> T getInstance(T object) {
        //代理类生成工具（Enhancer）
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(object.getClass());
        // 设置回调进行拦截
        enhancer.setCallback(this);
        //enhancer.setCallback(new Cgliber());
        return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib dynamic proxy begin execute ...");
        Object object = methodProxy.invokeSuper(obj, args);
        System.out.println("cglib dynamic proxy end execute ...");
        return object;
    }
}
