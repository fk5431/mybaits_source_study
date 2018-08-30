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

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Clinton Begin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Arg {
  boolean id() default false;//ID 参数;标记出作为 ID 的结果可以帮助提高整体性能

  String column() default "";//数据库中的列名,或者是列的别名

  Class<?> javaType() default void.class;//一个 Java 类的完全限定名,或一个类型别名

  JdbcType jdbcType() default JdbcType.UNDEFINED;//JDBC 类型

  Class<? extends TypeHandler> typeHandler() default UnknownTypeHandler.class;//默认类型处理器

  String select() default "";//用于加载复杂类型属性的映射语句的 ID

  String resultMap() default "";//ResultMap 的 ID

  String name() default "";//构造方法形参的名字

  /**
   * @since 3.5.0
   */
  String columnPrefix() default "";//映射对应的属性，区分他们分别属于哪些类
}
