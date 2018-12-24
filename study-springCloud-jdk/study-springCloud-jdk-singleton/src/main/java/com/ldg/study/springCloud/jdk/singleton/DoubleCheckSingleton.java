package com.ldg.study.springCloud.jdk.singleton;

import com.ldg.study.springCloud.tools.serialize.SerializeUtil;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * 双重检查（Double-Check）
 * <pre>
 *  synchronized 和 volatile 共同保证了线程安全
 *      synchronized：保证只有一个线程进入
 *      volatile：阻止（singleton = new DoubleCheckSingleton()）内部[1-2-3]的指令重排，而是保证了在一个写操作（[1-2-3]）完成之前，不会调用读操作（if (instance == null)）
 * </pre>
 *
 * @author： ldg
 * @create date： 2018/12/24
 */
public class DoubleCheckSingleton implements Serializable {
    /**
     * 保证可见性
     * 禁止指令重排：它的赋值完成之前，不会调用读操作
     */
    private static volatile DoubleCheckSingleton instance;

    /**
     * 外部不可实例化
     */
    private DoubleCheckSingleton() {
    }

    public static DoubleCheckSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}

class TestDoubleCheckSingleton {
    public static void main(String[] args) {
        DoubleCheckSingleton obj1 = DoubleCheckSingleton.getInstance();
        DoubleCheckSingleton obj2 = DoubleCheckSingleton.getInstance();

        //1、对象是否相同
        System.out.println(new StringBuilder()
                .append("\r\n=========================================")
                .append("\n1、对象是否相同")
                .append("\r\n   ").append("obj1 = obj2 : ").append(obj1 == obj2));

        //2、反序列化后，是否为同一对象
        ByteArrayOutputStream byteArrayOutputStream = SerializeUtil.serializeToByteArray(obj1);
        DoubleCheckSingleton obj3 = SerializeUtil.deserializeByByteArray(byteArrayOutputStream);
        System.out.println(new StringBuilder()
                .append("\r\n=========================================")
                .append("\n2、反序列化后，是否为同一对象")
                .append("\r\n   ").append("obj1 hashcode = ").append(obj1.hashCode())
                .append("\r\n   ").append("obj3 hashcode = ").append(obj3.hashCode())
                .append("\r\n   ").append("obj1 hashcode = obj3 hashcode : ").append(obj1.hashCode() == obj3.hashCode())
                .append("\r\n   ").append("obj1 = obj3 : ").append(obj1 == obj3));
    }
}
