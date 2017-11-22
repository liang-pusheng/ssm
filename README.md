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
3.mybatis映射文件
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.seckill.dao.SeckillDao">
    <!--目的：为DAO接口方法提供sql语句配置-->
    <select id="queryById" parameterType="long" resultType="Demo">
        select * from demo where id = #{id}
    </select>
```
4.Spring和mybatis的整合配置文件spring-dao.xml，因为Spring和mybatis整合到一起，所以对数据库的管理就由mybatis交给了Spring
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
5.配置Spring对service层代码的管理
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!--扫描service包下所有使用注解的类型-->
    <context:component-scan base-package="org.demo.service"/>

    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--注入数据库连接池-->
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--配置基于注解的声明式事务，默认使用注解来管理事务行为-->
    <tx:annotation-driven transaction-manager="transactionManager"/>
</beans>
```
需要注意一点的就是事务的使用，当只有一个数据库操作时(比如只删除或者只查询)，其实是不需要使用事务的。

6.最后就是Spring和SpringMVC的整合了，其实这个是非常简单的，因为本身SpringMVC就是Spring的一部分，配置文件如下：
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <!--配置SpringMVC-->
    <!--开启SpringMVC注解模式-->
    <!-- 目的是可以简化配置：
        （1）自动注册DefaultAnnotationHandlerMappoing,AnnotationMehtodHandlerAdapter
        （2）提供一系列数据绑定、数字日期转化（format） @NumberFormat，@DataTimeFormat,xml,json默认读写支持
    -->
    <mvc:annotation-driven/>

    <!-- 处理静态资源
        （1）加入允许对静态资源的处理：js gif png
        （2）允许使用“/”做整体映射
    -->
    <mvc:default-servlet-handler/>

    <!--视图解析-->
    <bean id="internalResource" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/><!--使用到jsp的标签-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <!--扫描web相关的bean-->
    <context:component-scan base-package="org.demo.web"/>
</beans>
```
***
#### 几点注意
###### 1.由配置文件的顺序可以看出，我这个整合顺序是由底层向上的，所有有的同学喜欢从上向底层开发的话，就需要整理一下思路再参考我这个整合的步骤了。
###### 2.以上的配置都只是一个非常基础的配置，如果系统还有很多其他的功能的话，还需要自己根据具体需求进行详细的配置。
###### 3.最后但同样重要的是，这个SSM整合只是一个参考，可能会存在很多的不足和需要提升的地方，所有还请大家多多指教。
