package com.ldg.study.springCloud.tools.serialize;

import java.io.*;

/**
 * 序列化工具类
 *
 * @author： ldg
 * @create date： 2018/12/24
 */
public class SerializeUtil {
    public static <T> boolean serializeToFile(T object, String filePath) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
            out.writeObject(object);
            out.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("序列化到文件失败");
            return false;
        }
    }

    public static <T> T deserializeByFile(String filePath) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
            T instance = (T) in.readObject();
            in.close();
            return instance;
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("反序列化文件失败");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("反序列化文件失败");
        }
        return null;
    }


    public static <T> ByteArrayOutputStream serializeToByteArray(T object) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obs = new ObjectOutputStream(out);
            obs.writeObject(object);
            obs.close();
            return out;
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("序列化到字节失败");
        }
        return null;
    }

    public static <T> T deserializeByByteArray(ByteArrayOutputStream byteArray) {
        try {
            ByteArrayInputStream ios = new ByteArrayInputStream(byteArray.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(ios);
            //返回生成的新对象
            T cloneObj = (T) ois.readObject();
            ois.close();
            return cloneObj;
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("反序列化字节失败");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("反序列化字节失败");
        }
        return null;
    }
}
