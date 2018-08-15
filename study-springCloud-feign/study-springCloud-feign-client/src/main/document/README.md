#Spring Cloud Feign
方式二 ： feign 负载均衡
    
1、项目结构
    
    study-springCloud-feign-client  : 
        客户端，如：webapi、其它微服务
        
    study-springCloud-feign-feign   :
        微服务Api
        目的：
            暴露给客户端，提供给客户端调用；
        权限：
            只包含对外的接口以及Dto之类的信息；
        
    study-springCloud-feign-context :
        微服务Context
        目的：
            实现微服务APi；
        权限:
            服务内部实现，仅限通过接口方式对外暴露
            
2、负载均衡

    通过"study-springCloud-feign-feign"实现微服务的负载均衡，以达到高可用；
    
3、服务降级（hystrix）

    前提：依赖于feign，或独立的包“spring-cloud-starter-hystrix”
    作用：
        可对接口，或方法进行服务降级

4、实现方式

    通过spring-cloud-starter-eureka和@EnableDiscoveryClient使用并注册到服务注册中心
    
    通过spring-cloud-starter-eureka和@EnableDiscoveryClient使用注册中心并发现服务
    
    通过spring-cloud-starter-feign来实现负载均衡消费服务
    
    feign 通过RestTemplate访问     

5、访问入口
    项目：study-springCloud-feign-client
    Url ：http://localhost:1145/order/getFeign