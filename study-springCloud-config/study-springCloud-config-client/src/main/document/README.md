#Spring Cloud Config Client

客户端读取服务配置中心
    
1、配置原理：

    1.1、配置文件顺序：
        bootstrap.yml -> application.yml -> application-profile.yml
        解读：
            1、从bootstrap.yml读取
            2、从application.yml读取
            3、从application-profile.yml读取
            4、如果配置相同，从“application-profile.yml” 到"application.yml"依次覆盖
            
    1.2、通过“spring.profiles.include”读取通用配置

2、参考地址：
    
    官方说明：
        http://cloud.spring.io/spring-cloud-static/Edgware.RELEASE/single/spring-cloud.html#_git_ssh_configuration_using_properties
    
    网上说明：
        https://www.javazhiyin.com/819.html  
    
3、访问地址
    http://localhost:1015/config/get   