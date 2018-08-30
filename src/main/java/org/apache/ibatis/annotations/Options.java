/**
 *    Copyright 2009-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.annotations;

import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.StatementType;

import java.lang.annotation.*;

/**
 * @author Clinton Begin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
//提供访问大范围的交换和配置选项的入口，它们通常在映射语句上作为属性出现
public @interface Options {
  /**
   * The options for the {@link Options#flushCache()}.
   * The default is {@link FlushCachePolicy#DEFAULT}
   */
  public enum FlushCachePolicy {
    /** <code>false</code> for select statement; <code>true</code> for insert/update/delete statement. */
    DEFAULT,
    /** Flushes cache regardless of the statement type. */
    TRUE,
    /** Does not flush cache regardless of the statement type. */
    FALSE
  }

  boolean useCache() default true;//将其设置为 true，将会导致本条语句的结果被二级缓存

  FlushCachePolicy flushCache() default FlushCachePolicy.DEFAULT;//将其设置为 true，任何时候只要语句被调用，都会导致本地缓存和二级缓存都会被清空

  ResultSetType resultSetType() default ResultSetType.FORWARD_ONLY;//FORWARD_ONLY，SCROLL_SENSITIVE 或 SCROLL_INSENSITIVE 中的一个

  StatementType statementType() default StatementType.PREPARED;//statement 可以声明为callable 的调用存储过程

  int fetchSize() default -1;//这是尝试影响驱动程序每次批量返回的结果行数和这个设置值相等

  int timeout() default -1;//这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数(默认依赖驱动)

  boolean useGeneratedKeys() default false;//取出 主键自增的值

  String keyProperty() default "";//唯一标记一个属性

  String keyColumn() default "";//通过生成的键值设置表中的列名
  
  String resultSets() default "";//这个设置仅对多结果集的情况适用，它将列出语句执行后返回的结果集并每个结果集给一个名称，名称是逗号分隔的。
}
