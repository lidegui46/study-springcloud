#Spring Cloud Zuul
    网关，通过配置路由进行访问，路由包含2种试：url 和 服务名(需eureka注册)
    
1、项目结构
    
    study-springCloud-zuul-gateway  : 
        网关服务

2、实现方式

    2.1、Url
        如下配置：
            api-url:
              path: /api-a/**
              url: http://localhost:9001
    2.2、服务名
        如下配置：
            feign-context:
              path: /feign-context/**
              serviceId: feign-context

3、访问入口

    项目：study-springCloud-zuul-gateway
    Url ：http://localhost:1150/feign-context/hello?code=asdfa
    
4、作用

    通过网关统一对外暴露服务，在网关可统一进行拦截处理，如：访问权限
    
5、参考地址

    https://www.javazhiyin.com/809.html    