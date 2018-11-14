package com.ldg.study.springCloud.socket.io.stream;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BufferedStreamTest {
    public static void main(String[] args) throws FileNotFoundException {
        String path = "a.txt";
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(path));
        DataInputStream input2 = new DataInputStream(new FileInputStream(path));

    }
}
