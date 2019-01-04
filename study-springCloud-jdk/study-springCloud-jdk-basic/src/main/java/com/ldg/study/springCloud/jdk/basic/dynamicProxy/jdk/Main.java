package com.ldg.study.springCloud.jdk.basic.dynamicProxy.jdk;

import java.lang.reflect.Proxy;

/**
 * @author： ldg
 * @create date： 2019/1/4
 */
public class Main {
    public static void main(String[] args) {
        proxyByInStance();
        proxyByService();
    }

    private static void proxyByInStance() {
        ManPeopleServiceImpl manPeopleService = new ManPeopleServiceImpl();

        PeopleInvocationHandler peopleInvocationHandler = new PeopleInvocationHandler(manPeopleService);

        PeopleService peopleService = (PeopleService) Proxy.newProxyInstance(manPeopleService.getClass().getClassLoader()
                , manPeopleService.getClass().getInterfaces()
                , peopleInvocationHandler);
        peopleService.eat();
    }

    private static void proxyByService() {
        PeopleInvocationHandler peopleInvocationHandler = new PeopleInvocationHandler(new ManPeopleServiceImpl());

        PeopleService peopleService = (PeopleService) Proxy.newProxyInstance(PeopleService.class.getClassLoader()
                , new Class[]{PeopleService.class}
                , peopleInvocationHandler);
        peopleService.eat();
    }
}
