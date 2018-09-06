---
title : mybatis源码学习
date : 2018-08-28
author : 冯凯
---

# mybatis 源码中包的作用

- annotations 包含所有mapper中常用的注解（✔）

- binding 将mapper接口绑定配置的相关类

- builder 包含configuration的所有构建器 （✔）还需要在回来看一遍
    - BaseBuild 构建器基类
    - XMLConfigBuilder 解析XMLconfiguration配置文件
    - XMLMapperBuilder 解析Mapper.xml配置文件
    - XMLStatementBuilder 解析select,insert,update等标签
    - MapperAnnotationBuilder 注解方式

- cache 缓存 （✔）
    - 缓存的实现
    - 包括各种缓存的装饰器
    - 二级缓存的实现

- cursor
    - 游标方式查询数据
    
- datasource 数据源/连接池 （✔）
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

- session 主要实现SqlSession功能（最核心的）（✔）

- transaction 事务

- type 类处理器 （✔）

## type 

- JdbcType 包装了下java.sql.Types

- TypeHandler  类处理器 

- BaseTypeHandler 类处理器的基类

> ByteArrayUtils byte 和 Byte 的转换

> 其他的类处理器基本都是实现的BaseTypeHandler，然后封装了PreparedStatement设置不同类型（jdbc的类型）作为setNonNullParameter。
> getNullableResult也是类似

> session中Configuration应该是mybatis绝大多数的配置项，包括初始化xml文件加载的mapper文件也存在其中（等）；
> 主要使用DefaulSqlSession实现了SqlSssion，其中主要用执行对sql进行执行和返回结果

- 20180901 接下来先读builder包中的内容把
    
    > builder用了很多建造者模式，对xml、mapper、parameter、result等的进行解析构建，用到了些mapping 和 parse下的方法，需要结合然后在理一遍

- cache

    > Cache接口类定义的一些操作，具体的东西的都得实现类去具体做实现，CacheKey是缓存key（用到了原型模式进行clone）
    > NullCacheKey 是null值的缓存key，里面不能进行增加元素，直接就抛异常了，（异常这个完全是为了划分结构啊啊）
    > PerpetualCache就是个永久缓存，里面就是一个唯一标识ID和一个hashmap实现的 - -
    > decorators包下的还没看，看命名就是各种cache的实现方式，看包名，难道用到了装饰器模式？？
    > decorators包下的的类都是用到了装饰器模式，对缓存类进行了又一步的包装，有阻塞的，fifo，lru，logging（打印命中率）
    > schedule 调度的  serialized序列化的*节约空间  soft软引用 week徐引用 synchronized 同步的 ，事务的

- datasource
    
    > datasource 数据源的包，mybatis做了三种实现，jndi，pooled，unpooled，一般我们使用的时候都使用第三方的连接池，
    > 应该是用的mybatis的jndi

- jdbc

- parsing

- mapping

- binding

- scripting

- executor

- cursor

- transaction

- plugin