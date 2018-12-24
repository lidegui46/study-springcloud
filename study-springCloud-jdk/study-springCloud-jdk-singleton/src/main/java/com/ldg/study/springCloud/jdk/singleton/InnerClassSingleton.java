package com.ldg.study.springCloud.jdk.singleton;

import com.ldg.study.springCloud.tools.serialize.SerializeUtil;

import java.io.ByteArrayOutputStream;

/**
 * 内部类加载机制 - 单例械
 * <pre>
 *  原理：通过 jvm clasLoader 保证同步，线程安全
 *  类加载 知识点：
 *      1. new一个对象时
 *      2. 使用反射创建它的实例时
 *      3. 子类被加载时，如果父类还没被加载，就先加载父类
 *      4. jvm启动时执行的主类会首先被加载
 *      5. 一些其他的实现方式
 *          静态内部类：
 *              内部类SingletonHolder是一个饿汉式的单例实现，在SingletonHolder初始化的时候会由ClassLoader来保证同步，使INSTANCE是一个真单例。
 *              由于SingletonHolder是一个内部类，只在外部类的Singleton的getInstance()中被使用，所以它被加载的时机也就是在getInstance()方法第一次被调用的时候。
 * </pre>
 *
 * @author： ldg
 * @create date： 2018/12/24
 */
public class InnerClassSingleton {
    private static class SingletonHolder {
        private static final InnerClassSingleton INSTANCE = new InnerClassSingleton();
    }

    private InnerClassSingleton() {
    }

    public static final InnerClassSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}

class TestInnerClassSingleton {
    public static void main(String[] args) {
        InnerClassSingleton obj1 = InnerClassSingleton.getInstance();
        InnerClassSingleton obj2 = InnerClassSingleton.getInstance();

        //1、对象是否相同
        System.out.println(new StringBuilder()
                .append("\r\n=========================================")
                .append("\n1、对象是否相同")
                .append("\r\n   ").append("obj1 = obj2 : ").append(obj1 == obj2));

        //2、反序列化后，是否为同一对象
        ByteArrayOutputStream byteArrayOutputStream = SerializeUtil.serializeToByteArray(obj1);
        InnerClassSingleton obj3 = SerializeUtil.deserializeByByteArray(byteArrayOutputStream);
        System.out.println(new StringBuilder()
                .append("\r\n=========================================")
                .append("\n2、反序列化后，是否为同一对象")
                .append("\r\n   ").append("obj1 hashcode = ").append(obj1.hashCode())
                .append("\r\n   ").append("obj3 hashcode = ").append(obj3.hashCode())
                .append("\r\n   ").append("obj1 hashcode = obj3 hashcode : ").append(obj1.hashCode() == obj3.hashCode())
                .append("\r\n   ").append("obj1 = obj3 : ").append(obj1 == obj3));
    }
}
