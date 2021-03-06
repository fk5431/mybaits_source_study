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
        
- jdbc jdbc的一些操作 sql操作 （✔）

- lang UsesJava7，UsesJava8注解标识那些可以使用jdk7api，那些可以使用jdk8api （✔）

- logging 日志 （✔）

- mapping 配置文件与实体对象的映射功能 （✔）

- parsing 解析 工具包     （✔）

- plugin 拦截器功能实现 （✔）

- reflection 反射 （✔）

- scripting 动态sql语言实现

- session 主要实现SqlSession功能（最核心的）（✔）

- transaction 事务

- type 类处理器 （✔）

## type 

- JdbcType 包装了下java.sql.Types
    
  >  - TypeHandler  类处理器 
    
  >  - BaseTypeHandler 类处理器的基类

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
    > AbstractSQL 类有两个内部类， SafeAppendable实现了一个安全的添加，SQLStatement 实现了对sql的拼接，
    > AbstractSQL 对外暴露，进行给SQLStatement中的变量复制，最终完成sql的拼接,就一个abstract方法getSelf()
    > Null就是枚举了一些类处理器，ScriptRunner是脚本处理器（还没仔细看完），SqlRunner是sql运行器，这两个都是
    > 可以用来进行单元测试的工具，SqlRunner中进行对PreparedStatement设置参数的时候，如果args instanceof Null则参数为null
    > SQL 中的内容都是在父类AbstractSQL中实现的
    > ScriptRunner 就是脚本的执行和处理，有一些参数的设置可能方便的去调试
    
- parsing

    > parsing中实现了两部分内容，首先是标志解析器，定了一个通用的解析器，具体的解析行为可以有持有实例的（继承于TokenHandler）
    类实现
    > 第二部分就是对xml文档的解析和使用，对org.w3c.dom.Node的包装 和 封装的一些jdk的类
    
- mapping
    
    > ParameterMapping 参数映射,存储了一个参数映射的属性 ParameterMode 参数映射的枚举  BoundSql 绑定sql
    > CacheBuilder 缓存构建器 实现对了对缓存的创建，并且对缓存实例进行了装饰
    > mapping中更多是定义了参数映射，结果映射，和绑定的sql的类，可以进行构建，解析应该不是这，之后在回来看一遍
    
- binding
    
    > 将mapper接口绑定，就是通过代理模式，调用mapper接口中的方法都是经过代理模式中的invoke方法，去执行sql然后返回结果

- scripting

    > 

- executor

- cursor
    
    > cursor里面东西很少，就是封装了下，使用游标的方式去读数据

- transaction

- plugin

    > 实现的mybatis中的拦截器，可以通过实现Interceptor类，并通过注解来指定要拦截的类和方法（指定参数）
    > 通过动态代理的方式返回一个object，在调用指定拦截方式的时候去实现拦截，插入自己的逻辑，比较巧妙的使用了代理模式
    > 还可以通过拦截器链来实现挨个拦截，责任链的感觉