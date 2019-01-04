package com.ldg.study.springCloud.jdk.basic.resourceLoader;


import org.springframework.core.io.UrlResource;

import java.net.URL;

/**
 * http资源
 * <pre>
 *     网络文件资源
 *     作为 URL 加载。
 * </pre>
 *
 * @author： ldg
 * @create date： 2018/12/26
 */
public class FtpResourceLoader {

    public static void main(String[] args) {
        loadFileData();
    }

    private static void loadFileData() {
        try {
            UrlResource resource = new UrlResource(new URL("ftp://csdnimg.cn/release/phoenix/template/css/chart-3456820cac.css"));
            PrintResourceUtil.loadFileData("区域编码", resource);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
