package com.ldg.study.springCloud.socket.io.piped;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 管道输出流
 *
 * @author： ldg
 * @create date： 2018/11/13
 */
class Send implements Runnable {
    private PipedOutputStream pos = null;

    public Send() {
        this.pos = new PipedOutputStream();
    }

    public PipedOutputStream getPos() {
        return this.pos;
    }

    @Override
    public void run() {
        String str = "Hello World!!!";
        try {
            this.pos.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.pos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 管道输入流
 */
class Receive implements Runnable {
    private PipedInputStream pis = null;

    public Receive() {
        this.pis = new PipedInputStream();
    }

    public PipedInputStream getPis() {
        return this.pis;
    }

    @Override
    public void run() {
        byte[] byteArr = new byte[1024];
        int len = 0;
        try {
            len = this.pis.read(byteArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.pis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //注意，这里是把读入的数组的数据输出，而不是PipeInputStream实例对象输出，
        System.out.println("接收的内容为：" + new String(byteArr, 0, len));
    }
}

public class Piped {
    public static void main(String args[]) {
        Send s = new Send();
        Receive r = new Receive();
        try {
            // 连接管道
            s.getPos().connect(r.getPis());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(s).start();
        new Thread(r).start();
    }
}