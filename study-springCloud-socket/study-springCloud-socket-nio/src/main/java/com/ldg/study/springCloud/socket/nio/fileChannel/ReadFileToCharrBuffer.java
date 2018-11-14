package com.ldg.study.springCloud.socket.nio.fileChannel;

import com.ldg.study.springCloud.socket.nio.utils.NioUtils;

import java.io.IOException;

/**
 *
 */
public class ReadFileToCharrBuffer {
    public static void main(String[] args) throws IOException {
        String file = "D:\\Project\\study\\study-springcloud\\study-springCloud-socket\\study-springCloud-socket-nio\\src\\main\\java\\com\\ldg\\study\\springCloud\\socket\\nio\\fileChannel\\files\\test.txt";
        System.out.println(NioUtils.reileToCharBuffer(file, 20));
    }
}
