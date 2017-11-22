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
```
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://127.0.0.1:3306/数据库名
username=用户名
password=密码
```
- logback配置文件
```
<?xml version="1.0" encoding="UTF-8" ?>
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
</configuration>
```
2.mybatis全局配置文件mybatis-config.xml
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <!--配置全局属性-->
    <settings>
        <!--使用jdbc的getGeneratedKeys 获取数据库自增主键值-->
        <setting name="useGeneratedKeys" value="true"/>
        <!--使用列名替换别名  默认：true
            select name as title from table
            name是列名 title是实体类对应name的属性
        -->
        <setting name="useColumnLabel" value="true"/>
        <!--开启驼峰命名转换：Table(create_time)->entity(createTime)-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
</configuration>
```
3.Spring和mybatis的整合配置文件spring-dao.xml，因为Spring和mybatis整合到一起，所以对数据的管理就由mybatis交给了Spring
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">

    <!--Spring配置整合mybatis-->

    <!--配置数据库相关参数的properties属性-->
    <context:property-placeholder location="classpath:jdbc.properties"/>
    <!--配置数据库连接池-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!--配置连接池属性-->
   
        <!--配置c3p0连接池的私有属性-->
        <property name="maxPoolSize" value="30"/>
        <property name="minPoolSize" value="10"/>
        <!--关闭连接后不自动commit-->
        <property name="autoCommitOnClose" value="false"/>
        <!--获取连接超时时间-->
        <property name="checkoutTimeout" value="3000"/>
        <!--当连接失败重试次数-->
        <property name="acquireRetryAttempts" value="2"/>
    </bean>

    <!--配置SqlSessionFactory对象-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--注入数据库连接池-->
        <property name="dataSource" ref="dataSource"/>
        <!--配置mybatis全局配置文件：mybatis-config.xml-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <!--扫描entity包 使用别名-->
        <property name="typeAliasesPackage" value="org.demo.entity"/>
        <!--扫描sql配置文件：包含sql语句的xml文件(实体映射文件)-->
        <property name="mapperLocations" value="classpath:mapper/*.xml"/>
    </bean>

    <!--配置扫描DAO接口包，动态实现DAO接口，注入到Spring容器中-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!--注入SqlSessionFactory-->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <!--配置扫描DAO接口包-->
        <property name="basePackage" value="org.demo.dao"/>
    </bean>
</beans>
```

