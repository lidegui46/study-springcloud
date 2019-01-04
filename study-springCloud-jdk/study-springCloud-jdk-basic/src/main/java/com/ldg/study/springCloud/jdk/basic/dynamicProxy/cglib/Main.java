package com.ldg.study.springCloud.jdk.basic.dynamicProxy.cglib;

/**
 * @author： ldg
 * @create date： 2019/1/4
 */
public class Main {
    public static void main(String[] args) {
        Cgliber cgliber = new Cgliber();
        PeopleServiceImpl peopleServiceProxy =  cgliber.getInstance(new PeopleServiceImpl());
        peopleServiceProxy.eat();
    }
}
