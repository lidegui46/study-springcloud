package util;

import io.netty.handler.codec.http.QueryStringDecoder;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * @author： ldg
 * @create date： 2018/11/21
 */
public class UrlUtil {
    /**
     * Url参数转换为Map
     *
     * @param url
     * @return
     */
    public static Map<String, List<String>> getUrlParam(String url) {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(URLDecoder.decode(url));
        return queryStringDecoder.parameters();
    }
}
