package com.ldg.study.springCloud.distribute.id;

/**
 * Twitter的分布式自增ID雪花算法snowflake
 * <p>
 * 参考地址：https://mp.weixin.qq.com/s/ZNSnRTmL0FX8THQBIA98Pw
 * Author:  ldg
 * Date:    2019/9/28 9:50
 * Desc:    this is file description
 */
public class SnowFlake {
    /**
     * 起始的时间戳，固定值，设置后不能随意变更
     */
    private final static long START_STMP = 1569675329728L;// System.currentTimeMillis();

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT = 5;   //机器标识占用的位数
    private final static long DATACENTER_BIT = 5;//数据中心占用的位数

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT); // 最大 数据中心 数量
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT); // 最大 工作机器 数量
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT); // 基本大 序列号

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT; // 机器 左移 bit
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT; // 数据中心 左移 bit
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT; // 时间戳 左移 bit

    private final long datacenterId;  // 数据中心
    private final long machineId;     // 机器标识
    private volatile long sequence = 0L; //序列号
    private volatile long lastStmp = -1L;//上一次时间戳

    /**
     * 雪花算法
     * -----------------------------------------------
     * 思考：数据中心 和 工作机器 在集群情况下怎么按实际情况设置？
     * -----------------------------------------------
     *
     * @param datacenterId 数据中心数量
     * @param machineId    机器数量
     */
    public SnowFlake(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }
}