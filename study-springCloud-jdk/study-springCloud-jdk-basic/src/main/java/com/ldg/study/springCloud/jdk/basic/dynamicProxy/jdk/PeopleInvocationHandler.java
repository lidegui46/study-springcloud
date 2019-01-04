package com.ldg.study.springCloud.jdk.basic.dynamicProxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author： ldg
 * @create date： 2019/1/4
 */
public class PeopleInvocationHandler implements InvocationHandler {
    private PeopleService peopleService;
    public PeopleInvocationHandler(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdk dynamic proxy begin execute ...");
        Object object = method.invoke(peopleService, args);
        System.out.println("jdk dynamic proxy end execute ...");
        return object;
    }
}
