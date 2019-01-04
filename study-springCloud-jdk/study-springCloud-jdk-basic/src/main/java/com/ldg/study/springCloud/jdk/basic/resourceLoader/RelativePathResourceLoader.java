package com.ldg.study.springCloud.jdk.basic.resourceLoader;

import org.springframework.util.ResourceUtils;

import java.io.InputStream;

/**
 * 相对路径资源
 * <pre>
 *     路径以”/“开对，如：/data/config.xml
 *     根据 ApplicationContext 进行判断。
 * </pre>
 *
 * @author： ldg
 * @create date： 2018/12/26
 */
public class RelativePathResourceLoader {
    private static final String AREA_RESOURCE_FILE = "/json/area_data.json";
    private static final String AREA_RESOURCE_FILE2 = "json/area_data.json";
    private static final String PROPERTIES_RESOURCE_FILE = "/config.properties";

    public static void main(String[] args) {
        loadFileDataByClass();
        //loadPropertiesByClass();
    }

    /**
     * 以”Class“方式加载资源文件
     * <pre>
     *     路径 以”/“开头
     * </pre>
     */
    private static void loadFileDataByClass() {
        try {
            InputStream resourceAsStream;
            resourceAsStream = Class.forName("com.ldg.study.springCloud.jdk.basic.resourceLoader.RelativePathResourceLoader").getResourceAsStream(AREA_RESOURCE_FILE);
            //resourceAsStream = this.getClass().getResourceAsStream(AREA_RESOURCE_FILE);
            resourceAsStream = RelativePathResourceLoader.class.getResourceAsStream(AREA_RESOURCE_FILE);
            resourceAsStream = ClassLoader.getSystemResourceAsStream(AREA_RESOURCE_FILE2);

            PrintResourceUtil.loadFileData("区域编码", resourceAsStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 以”Class“方式加载配置资源文件
     * <pre>
     *     路径 以”/“开头
     * </pre>
     */
    private static void loadPropertiesByClass() {
        InputStream resourceAsStream = RelativePathResourceLoader.class.getResourceAsStream(PROPERTIES_RESOURCE_FILE);
        PrintResourceUtil.loadPropertiesData("区域编码", resourceAsStream);
    }
}
