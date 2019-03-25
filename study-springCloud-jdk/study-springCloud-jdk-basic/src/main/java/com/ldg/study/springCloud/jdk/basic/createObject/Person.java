package com.ldg.study.springCloud.jdk.basic.createObject;

import lombok.Data;

/**
 * @author： ldg
 * @create date： 2019/3/25
 */
public class Person {
    public Person() {
        this.name = "默认值";
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
