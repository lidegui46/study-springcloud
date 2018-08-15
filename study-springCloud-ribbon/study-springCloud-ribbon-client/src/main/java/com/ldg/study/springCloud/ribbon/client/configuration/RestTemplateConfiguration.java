package com.ldg.study.springCloud.ribbon.client.configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： ldg
 * @create date： 2018/8/14
 */
@Configuration
public class RestTemplateConfiguration {

    @Bean
    public SimpleClientHttpRequestFactory httpClientFactory() {
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setReadTimeout(10000);
        httpRequestFactory.setConnectTimeout(10000);
        return httpRequestFactory;
    }

    /**
     * 通过RestTemplate实现Spring cloud 负载均衡
     * <pre>
     *     此方法添加“@LoadBalanced” 注解后，负载均衡启效
     * </pre>
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        messageConverters.clear();
        messageConverters.add(buildStringHttpMessageConverter());
        messageConverters.add(buildFastJsonMessageConverter());
        return restTemplate;
    }

    public FastJsonHttpMessageConverter buildFastJsonMessageConverter() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        fastConverter.setFastJsonConfig(buildJsonConfig());
        fastConverter.setSupportedMediaTypes(buildSupportedMediaTypes());
        return fastConverter;
    }

    public StringHttpMessageConverter buildStringHttpMessageConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter();
        converter.setSupportedMediaTypes(buildSupportedMediaTypes());
        return converter;
    }

    private FastJsonConfig buildJsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        return fastJsonConfig;
    }

    private List<MediaType> buildSupportedMediaTypes() {
        List<MediaType> fastMediaTypes = new ArrayList<MediaType>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastMediaTypes.add(MediaType.TEXT_HTML);
        return fastMediaTypes;
    }
}
