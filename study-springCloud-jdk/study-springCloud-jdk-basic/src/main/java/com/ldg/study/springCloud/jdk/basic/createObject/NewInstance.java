package com.ldg.study.springCloud.jdk.basic.createObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author： ldg
 * @create date： 2019/3/25
 */
public class NewInstance {
    public static void main(String[] args) {
        //获取class
        Class<? extends Person> personClass = getPersonClass();
        //实例化无参构造方法
        instanceDefaultNoParamterConstructor(personClass);

        //实例化有参构造方法
        instanceParamterConstructor(personClass);
    }

    /**
     * 实例化有参构造方法
     *
     * @param personClass class类
     */
    private static void instanceParamterConstructor(Class<? extends Person> personClass) {
        try {
            Constructor<? extends Person> declaredConstructorByOne = personClass.getDeclaredConstructor(String.class);
            Person person1 = declaredConstructorByOne.newInstance("张三");
            System.out.println(person1.getName());


            Constructor<? extends Person> declaredConstructorByTwo = personClass.getDeclaredConstructor(String.class, Integer.class);
            Person person2 = declaredConstructorByTwo.newInstance("李四", 20);
            System.out.println(person2.getName());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实例化无参构造方法
     *
     * @param personClass class类
     */
    private static void instanceDefaultNoParamterConstructor(Class<? extends Person> personClass) {
        try {
            Person person = personClass.newInstance();
            System.out.println(person.getName());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Person Class 类
     *
     * @return
     */
    private static Class<? extends Person> getPersonClass() {
        Class<? extends Person> personClass = byInstance();
        personClass = byClass();
        personClass = byRelect();
        return personClass;
    }

    /**
     * 通过“反射”获取Person Class类
     *
     * @return Person Class
     */
    private static Class<? extends Person> byRelect() {
        try {
            return (Class<? extends Person>) Class.forName("com.ldg.study.springCloud.jdk.basic.createObject.Person");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过“类”获取Person Class类
     *
     * @return Person Class
     */
    private static Class<? extends Person> byClass() {
        return Person.class;
    }


    /**
     * 通过“实例化”获取Person Class类
     *
     * @return Person Class
     */
    private static Class<? extends Person> byInstance() {
        Person person = new Person();
        return person.getClass();
    }
}

