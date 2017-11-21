## ssm框架整合
### 关于Spring、SpringMVC、mybatis框架的记录
学习完Spring、SpringMVC、mybatis后，觉得它们各自的知识点都很熟悉，但是把它们放到一起做项目时却不知所措，因此就学习了如何将它们整合到一起并详细记录下来。
***
#### 需要的包
###### 1.Spring相关的包
- spring-core
- spring-beans
- spring-context
- spring-aop
- spring-tx
- spring-test
- spring-expression
###### 2.SpringMVC相关的包
- spring-web
- spring-webmvc
###### 3.mybatis相关的包
- mybatis
- mybatis-spring
###### 4.日志相关的包
- logback-core
- logback-classic
- slf4j-api
###### 5.数据库相关的包
- mysql-connector-java
- c3p0

总的看来需要的包并不是特别的多，如果需要使用其他功能的话，如缓存，添加相应的包即可。
***
#### 具体步骤
整合的过程是：mybatis和Spring整合，然后在将Spring和SpringMVC整合

1.全局配置文件和属性文件
- jdbc属性文件

`driver=com.mysql.jdbc.Driver
url=jdbc:mysql://127.0.0.1:3306/数据库名
username=用户名
password=密码`
- logback配置文件

`<?xml version="1.0" encoding="UTF-8" ?>
<configuration debug="true">
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <!-- encoders are  by default assigned the type
             ch.qos.logback.classic.encoder.PatternLayoutEncoder -->
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="debug">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>`

