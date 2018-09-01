---
title : mybatis源码学习
date : 2018-08-28
author : 冯凯
---

# mybatis 源码中包的作用

- annotations 包含所有mapper中常用的注解（✔）

- binding 将mapper接口绑定配置的相关类

- builder 包含configuration的所有构建器
    - BaseBuild 构建器基类
    - XMLConfigBuilder 解析XMLconfiguration配置文件
    - XMLMapperBuilder 解析Mapper.xml配置文件
    - XMLStatementBuilder 解析select,insert,update等标签
    - MapperAnnotationBuilder 注解方式

- cache 缓存
    - 缓存的实现
    - 包括各种缓存的装饰器
    - 二级缓存的实现

- cursor
    - 游标方式查询数据
    
- datasource 数据源/连接池
    - 数据源，包括jndi数据源和连接池
    
- exceptions 异常相关（✔）
    
- executor sql语句执行器，核心功能包
    
- io 读取资源文件 （✔）（没完全看懂）
        
- jdbc jdbc的一些操作 sql操作

- lang UsesJava7，UsesJava8注解标识那些可以使用jdk7api，那些可以使用jdk8api

- logging 日志 （✔）

- mapping 配置文件与实体对象的映射功能

- parsing 解析 工具包    

- plugin 拦截器功能实现

- reflection 反射 （✔）

- scripting 动态sql语言实现

- session 主要实现SqlSession功能（最核心的）

- transaction 事务

- type 类处理器 （✔）

## type 

- JdbcType 包装了下java.sql.Types

- TypeHandler  类处理器 

- BaseTypeHandler 类处理器的基类

> ByteArrayUtils byte 和 Byte 的转换

> 其他的类处理器基本都是实现的BaseTypeHandler，然后封装了PreparedStatement设置不同类型（jdbc的类型）作为setNonNullParameter。
> getNullableResult也是类似















