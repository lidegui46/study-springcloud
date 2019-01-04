package com.ldg.study.springCloud.jdk.basic.resourceLoader;


import org.apache.commons.lang.CharEncoding;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 文件资源
 * <pre>
 *     路径以”file:“开头，如：file:/a/b.xml
 *     作为 URL 从文件系统中加载。
 * </pre>
 *
 * @author： ldg
 * @create date： 2018/12/26
 */
public class FileResourceLoader {
    /**
     * 文件系统决定路径
     */
    private static final String CLASS_PATH = "file:D:\\Project\\study\\study-springcloud\\study-springCloud-jdk\\study-springCloud-jdk-basic\\src\\main\\resources\\json\\area_data.json";

    public static void main(String[] args) {
        loadFileData();
    }

    private static void loadFileData() {
        try {
            UrlResource resource = new UrlResource(new URL(CLASS_PATH));
            PrintResourceUtil.loadFileData("区域编码", resource);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
