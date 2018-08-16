#Spring Cloud Ribbon Client
负载均衡

1、项目结构
    
    study-springCloud-ribbon-client  :
        客户端，如：webapi、其它微服务
    
    study-springCloud-ribbon-service :
        微服务Context
        目的：
            实现微服务APi；
        权限:
            服务内部实现，仅限通过接口方式对外暴露
    
2、实现方式

    通过spring-cloud-starter-eureka和@EnableDiscoveryClient使用并注册到服务注册中心
    
    通过spring-cloud-starter-eureka和@EnableDiscoveryClient使用注册中心并发现服务
    
    通过spring-cloud-starter-ribbon来实现负载均衡消费服务
    
    ribbon通过RestTemplate访问 

3、实现
    
    2.1、@EnableDiscoveryClient   主应用程序添加注解
    2.2、RestTemplateConfiguration 类增加方法“restTemplate”,返回"RestTemplate"，且增加“@LoadBalanced”注解（必须添加）

4、访问入口
    项目：study-springCloud-ribbon-client
    Url ：http://localhost:1120/order/get

5、工作步骤
    
    第一步：
        先选择 EurekaServer ,它优先选择在同一个区域内负载较少的server. 
    
    第二步：
        根据用户指定的策略，在从server取到的服务注册列表中选择一个地址。 
    其中Ribbon提供了多种策略：比如轮询、随机和根据响应时间加权。
    
6、自定义负载均衡算法
    参考地址：https://www.javazhiyin.com/5130.html

#Spring Cloud Hystrix 服务容错
使用 断路器 进行 服务容错
    
1、实现
    
    1.1、@EnableCircuitBreaker   主应用程序添加注解
    1.2、容错方法上增加“@HystrixCommand(fallbackMethod = "userFallback")”注解，然后再增加容错方法（“userFallback”）
        注：容错方法的参数必须和主方法一样

参考地址：
    https://www.javazhiyin.com/category/springcloud