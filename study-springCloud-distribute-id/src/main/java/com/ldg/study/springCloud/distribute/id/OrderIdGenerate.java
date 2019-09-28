package com.ldg.study.springCloud.distribute.id;

/**
 * 订单 id 生成器
 * Author:  ldg
 * Date:    2019/9/28 20:45
 * Desc:    this is file description
 */
public class OrderIdGenerate {
    /**
     * 雪花算法
     */
    private final static SnowFlake snowFlake = new SnowFlake(1, 1);

    private OrderIdGenerate() {
        throw new RuntimeException("Order IdGenerate 不能实例化");
    }

    public static void main(String[] args) {
        prefixFromDD();
    }

    private static void idGenerate(String prefix) {
        long start = System.currentTimeMillis();
        //for (int i = 0; i < 1000000; i++) {
        for (int i = 0; i < 1; i++) {
            System.out.println(prefix + snowFlake.nextId());
        }
        System.out.println(start);
        System.out.println(System.currentTimeMillis());
    }

    public static void prefixFromNothing() {
        idGenerate("");
    }

    public static void prefixFromDD() {
        idGenerate("DD");
    }
}
