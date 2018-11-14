package com.ldg.study.springCloud.socket.io.stream;

import java.io.IOException;
import java.io.OutputStream;

public class FileOutputStream {
    public static void main(String[] args) throws IOException {

        // append
        OutputStream outputStream1 = new java.io.FileOutputStream("aa",true);

        // overwrite
        OutputStream outputStream2 = new java.io.FileOutputStream("aa",false);

        // 忽略缓存，直接刷到存储媒介
        outputStream1.flush();
    }
}
