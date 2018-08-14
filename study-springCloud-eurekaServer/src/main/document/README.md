#Spring Cloud Eureka Server
服务注册中心，用于服务的注册 与 发现

1、实现方式

通过spring-cloud-starter-eureka-server和@EnableEurekaServer实现服务注册中心

Spring Cloud  中的“Discovery Service”有多种 实现

    eureka
    consul
    zookeeper
2、解释

2.1、@EnableDiscoveryClient

    基于 spring-cloud-commons 依赖，并且在classpath中实现；
    场景：consul、zookeeper

2.2、@EnableEurekaClient

    基于 spring-cloud-netflix 依赖，只能为eureka作用；
    场景：zureka
    
3、作用域

如果选用的注册中心是eureka，那么就推荐@EnableEurekaClient;

如果是其他的注册中心，那么推荐使用@EnableDiscoveryClient
