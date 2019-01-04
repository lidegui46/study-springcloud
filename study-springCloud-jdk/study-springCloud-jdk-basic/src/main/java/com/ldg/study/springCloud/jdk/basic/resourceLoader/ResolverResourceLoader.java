package com.ldg.study.springCloud.jdk.basic.resourceLoader;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author： ldg
 * @create date： 2018/12/26
 */
public class ResolverResourceLoader {
    static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    public static void main(String[] args) {
        try {
            readFromClasspath();
            readFromHttp();
            readFromFile();
            readFromFTP();
            readFromNoPreFix();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     *
     * @Title: readFromClasspath
     *
     * @Description: 读取 classpath: 地址前缀的文件
     *
     * @throws IOException
     *
     * @return: void
     */

    public static void readFromClasspath() throws IOException {
        Resource[] resources = resourcePatternResolver.getResources("classpath*:json/area_data.json");

        for (Resource resource : resources) {
            System.out.println(resource.getDescription());
            readContent(resource);
        }

    }

    public static void readFromNoPreFix() throws IOException {
        Resource resource = resourcePatternResolver.getResource("json/area_data.json");
        System.out.println(resource.getDescription());
        readContent(resource);
    }

    /**
     *
     *
     * @Title: readFromFile
     *
     * @Description: 使用UrlResource从文件系统目录中装载资源，可以采用绝对路径或者相对路径
     *
     * @throws IOException
     *
     * @return: void
     */
    public static void readFromFile() throws IOException {
        Resource resource = resourcePatternResolver.getResource(
                "file:/D:/workspace/workspace-jee/HelloSpring/hello-spring4/src/main/java/com/xgj/conf/conf2/test2.xml");
        readContent(resource);
    }

    /**
     *
     *
     * @Title: readFromHttp
     *
     * @Description: 使用UrlResource从web服务器中加载资源
     *
     * @throws IOException
     *
     * @return: void
     */
    public static void readFromHttp() throws IOException {
        Resource resource = resourcePatternResolver.getResource("http://127.0.0.1:8080/hello-spring4/index.jsp");

        System.out.println(resource.getDescription());
        readContent(resource);
    }

    /**
     *
     *
     * @Title: readFromFTP
     *
     * @Description: 这里只演示写法，因为这个服务器要求用户名和密码，其实是无法读取的。
     *
     * @throws IOException
     *
     * @return: void
     */
    public static void readFromFTP() throws IOException {
        Resource resource = resourcePatternResolver
                .getResource("ftp://172.25.243.81/webserver/config/logback.xml");
    }

    /**
     *
     *
     * @Title: readContent
     *
     * @Description: 读取获取到的资源文件的内容
     *
     * @param resource
     * @throws IOException
     *
     * @return: void
     */
    public static void readContent(Resource resource) throws IOException {
        InputStream ins = resource.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int i;
        while ((i = ins.read()) != -1) {
            bos.write(i);
        }
        System.out.println("读取的文件:" + resource.getFilename() + ",/n内容:/n" + bos.toString());
    }
}
