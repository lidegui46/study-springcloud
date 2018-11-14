package com.ldg.study.springCloud.socket.nio.fileChannel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 把文件内容写入到另一个文件
 */
public class ReadFileToFile {
    public static void main(String[] args) throws IOException {
        String from = "D:\\Project\\study\\study-springcloud\\study-springCloud-socket\\study-springCloud-socket-nio\\src\\main\\java\\com\\ldg\\study\\springCloud\\socket\\nio\\fileChannel\\files\\from.txt";
        String to = "D:\\Project\\study\\study-springcloud\\study-springCloud-socket\\study-springCloud-socket-nio\\src\\main\\java\\com\\ldg\\study\\springCloud\\socket\\nio\\fileChannel\\files\\to.txt";

        try (RandomAccessFile fromFile = new RandomAccessFile(from, "rw")) {
            FileChannel fromChannel = fromFile.getChannel();

            try (RandomAccessFile toFile = new RandomAccessFile(to, "rw")) {
                FileChannel toChannel = toFile.getChannel();

                long position = 0;
                long count = fromChannel.size();

                //toChannel.transferFrom(fromChannel, position, count);

                fromChannel.transferTo(position, count, toChannel);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
