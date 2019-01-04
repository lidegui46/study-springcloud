package com.ldg.study.springCloud.jdk.basic.dynamicProxy.jdk;

/**
 * @author： ldg
 * @create date： 2019/1/4
 */
public class WomenPeopleServiceImpl implements PeopleService {
    @Override
    public void eat() {
        System.out.println("women eat");
    }
}
