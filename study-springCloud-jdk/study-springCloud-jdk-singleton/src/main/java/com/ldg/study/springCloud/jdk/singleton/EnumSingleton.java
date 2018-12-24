package com.ldg.study.springCloud.jdk.singleton;


import com.ldg.study.springCloud.tools.serialize.SerializeUtil;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * 单例模式 - 枚举
 *
 * <pre>
 *      传统的两私有一公开（私有构造方法、私有静态实例(懒实例化/直接实例化)、公开的静态获取方法）涉及线程安全问题（即使有多重检查锁也可以通过反射破坏单例），
 *
 *      目前最为安全的实现单例的方法是通过内部静态enum的方法来实现，因为JVM会保证enum不能被反射并且构造器方法只执行一次。
 * </pre>
 *
 * @author： ldg
 * @create date： 2018/12/24
 */
public enum EnumSingleton implements Serializable {
    INSTANCE;

    public void getData() {

    }

    public static void main(String[] args) {
        EnumSingleton obj1 = EnumSingleton.INSTANCE;
        EnumSingleton obj2 = EnumSingleton.INSTANCE;

        // 获取数据
        obj1.getData();

        //1、对象是否相同
        System.out.println(new StringBuilder()
                .append("\r\n=========================================")
                .append("\n1、对象是否相同")
                .append("\r\n   ").append("obj1 = obj2 : ").append(obj1 == obj2));

        //2、反序列化后，是否为同一对象
        ByteArrayOutputStream byteArrayOutputStream = SerializeUtil.serializeToByteArray(obj1);
        EnumSingleton obj3 = SerializeUtil.deserializeByByteArray(byteArrayOutputStream);
        System.out.println(new StringBuilder()
                .append("\r\n=========================================")
                .append("\n2、反序列化后，是否为同一对象")
                .append("\r\n   ").append("obj1 hashcode = ").append(obj1.hashCode())
                .append("\r\n   ").append("obj3 hashcode = ").append(obj3.hashCode())
                .append("\r\n   ").append("obj1 hashcode = obj3 hashcode : ").append(obj1.hashCode() == obj3.hashCode())
                .append("\r\n   ").append("obj1 = obj3 : ").append(obj1 == obj3));
    }
}
