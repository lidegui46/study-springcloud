#Spring Cloud Config Server

服务配置中心

    作用：
        通过读取Git,SVN地址来获取对应配置
    
1、访问配置信息：

    通过浏览器查看服务器配置信息

    访问地址： http://localhost:1010/common/dev/master
        说明：
            http://localhost:1010       ：config-server 地址和端口
            common                      : application name
            dev                         : profile name   
            master                      : branch name   分支名称
            
        配置文件存放：
            gitUrl/{branch}/{application}-{profile}
            
2、参考地址：
    
    官方说明：
        http://cloud.spring.io/spring-cloud-static/Edgware.RELEASE/single/spring-cloud.html#_git_ssh_configuration_using_properties
    
    网上说明：
        https://www.javazhiyin.com/819.html  
    