package com.ldg.study.springCloud.jdk.basic.resourceLoader;

import org.apache.commons.lang.CharEncoding;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author： ldg
 * @create date： 2018/12/26
 */
public class PrintResourceUtil {
    public static void loadFileData(String title, Resource resource) {
        String line;
        StringBuilder sb = new StringBuilder("====================\r\n").append(title).append(":");
        sb.append("\r\n     文件名：").append(resource.getFilename());
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), CharEncoding.UTF_8))) {
            while ((line = br.readLine()) != null) {
                sb.append("\r\n     ").append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sb.toString());
    }

    public static void loadFileData(String title, InputStream inputStream) {
        String line;
        StringBuilder sb = new StringBuilder("====================\r\n").append(title).append(":");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, CharEncoding.UTF_8))) {
            while ((line = br.readLine()) != null) {
                sb.append("\r\n     ").append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sb.toString());
    }

    public static void loadPropertiesData(String title, InputStream inputStream) {
        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            StringBuilder sb = new StringBuilder("====================\r\n").append(title).append(":");

            sb.append("\r\n     name: ").append(properties.getProperty("name"));
            sb.append("\r\n     age: ").append(properties.getProperty("age"));
            System.out.println(sb.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
