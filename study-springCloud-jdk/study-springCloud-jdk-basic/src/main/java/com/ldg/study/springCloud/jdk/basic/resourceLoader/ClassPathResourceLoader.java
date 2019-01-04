package com.ldg.study.springCloud.jdk.basic.resourceLoader;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * class路径资源
 * <pre>
 *     路径以”classpath:“或"classpath*:"开对，如：classpath:/a/b.xml
 *     从classpath中加载。
 * </pre>
 *
 * @author： ldg
 * @create date： 2018/12/26
 */
public class ClassPathResourceLoader {
    public static void main(String[] args) {
        //loadFileDataByLoader();
        //loadFileDataByClassPath();
    }

    private static void loadFileDataByLoader() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/json/area_data.json");
        PrintResourceUtil.loadFileData("区域编码", resource);
    }

    private static void loadFileDataByClassPath() {
        Resource resource = new ClassPathResource("/json/area_data.json");
        PrintResourceUtil.loadFileData("区域编码", resource);
    }

    private static void loadXML() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
        //applicationContext.get
    }
}
