package com.ldg.study.springCloud.socket.nio.utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * capacity buffer的容量
 * position 操作的位置，写入的位置以及读取的位置
 * limit 写模式下，为最多写入的量；读模式下，为当前position
 * mask标记，reset恢复标记
 * <a href="https://www.cnblogs.com/niejunlei/p/5994130.html">图片参考</a>
 */
public class NioUtils {
    public static String reileToCharBuffer(String filePath, int bufferCapacity) throws IOException {
        try (RandomAccessFile accessFile = new RandomAccessFile(filePath, "rw")) {
            FileChannel fileChannel = accessFile.getChannel();

            StringBuilder sbContent = new StringBuilder();
            // 中文
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            CharBuffer charBuffer = CharBuffer.allocate(bufferCapacity);

            // 字节缓存区
            ByteBuffer byteBuffer = ByteBuffer.allocate(bufferCapacity);
            //把内容以字节读取到缓存区
            int bytesRead = fileChannel.read(byteBuffer);
            // buffer.put()也能写入buffer
            while (bytesRead != -1) {
                // 字节 写 切换到 读
                byteBuffer.flip();
                // 字节 转 char
                decoder.decode(byteBuffer, charBuffer, false);
                // 字符 写 切换到 读
                charBuffer.flip();
                //读取字符
                while (charBuffer.hasRemaining()) {
                    sbContent.append(charBuffer.get());
                }

                // buffer.rewind()重新读
                // buffer.mark()标记position buffer.reset()恢复

                // 清除缓冲区
                byteBuffer.clear();
                charBuffer.clear();
                // buffer.compact(); 清除读过的数据

                //继续读剩下的内容
                bytesRead = fileChannel.read(byteBuffer);
            }
            return sbContent.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
