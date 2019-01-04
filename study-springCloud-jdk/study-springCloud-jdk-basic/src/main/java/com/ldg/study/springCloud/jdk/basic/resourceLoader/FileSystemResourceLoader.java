package com.ldg.study.springCloud.jdk.basic.resourceLoader;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;

/**
 * 文件系统加载类
 *
 * @author： ldg
 * @create date： 2018/12/26
 */
public class FileSystemResourceLoader {

    /**
     * 文件系统决定路径
     */
    private static final String CLASS_PATH = "D:\\Project\\study\\study-springcloud\\study-springCloud-jdk\\study-springCloud-jdk-basic\\src\\main\\resources\\json\\area_data.json";

    public static void main(String[] args) {
        loadFileData();
    }

    private static void loadFileData() {
        WritableResource res = new FileSystemResource(CLASS_PATH);
        PrintResourceUtil.loadFileData("区域编码", res);
    }
}
