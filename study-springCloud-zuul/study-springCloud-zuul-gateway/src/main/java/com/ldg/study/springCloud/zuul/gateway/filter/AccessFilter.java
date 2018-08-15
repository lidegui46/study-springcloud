package com.ldg.study.springCloud.zuul.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Zuul 用来进行请求过滤
 *
 * @author： ldg
 * @create date： 2018/8/15
 */
//@Component
public class AccessFilter extends ZuulFilter {
    private final static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    /**
     * 判断该过滤器是否需要被执行
     * <pre>
     *     true : 表示该过滤器对所有请求都会生效
     * </pre>
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return false;
    }

    /**
     * ilterType 返回过滤器类型
     *
     * <pre>
     *      pre     : 请求执行之前filter
     *      route   : 处理请求，进行路由
     *      post    : 请求处理完成后执行的filter
     *      error   : 出现错误时执行的filter
     * </pre>
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * filterOrder 返回过滤器的执行顺序
     * <pre>
     *  当请求在一个阶段有多个过滤器是，需要根据该方法的返回值来依次执行
     * </pre>
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 过滤器的具体逻辑
     *
     * @return
     */
    @Override
    public Object run() {
//        RequestContext context = RequestContext.getCurrentContext();
//        HttpServletRequest request = context.getRequest();
//        Object accessToken = request.getParameter("accessToken");
//
//        logger.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
//        if (accessToken == null) {
//            context.setSendZuulResponse(false);
//            context.setResponseStatusCode(401);
//        }

        return null;
    }
}
