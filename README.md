# study-springcloud
学习研究 SpringCloud

项目目的：为了放到github同一个目录下，放在了一个git地址上

1、eureka server

    注册中心
    
2、spring cloud ribbon / spring cloud hystrix
    负载均衡 / 容错保护
    
    服务：
        study-springCloud-service-order（启动 1 个）
        study-springCloud-service-user（启动 2 个）
    入口：
        project : study-springCloud-service-order
        entry : OrderController
        url : http://localhost:1120/order/get
        
3、spring cloud feign
    负载均衡 和 容错保护 升级版
    

4、参考地址：

    官网地址：
    
        中文：https://springcloud.cc/        
       
        英文：http://cloud.spring.io
        
    网上地址：
        https://www.javazhiyin.com/category/springcloud    