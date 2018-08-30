/**
 *    Copyright 2009-2016 the original author or authors.
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

import org.apache.ibatis.mapping.StatementType;

import java.lang.annotation.*;

/**
 * @author Clinton Begin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
//selectKey 元素将会首先运行
public @interface SelectKey {
  String[] statement();

  String keyProperty();//selectKey 语句结果应该被设置的目标属性

  String keyColumn() default "";//匹配属性的返回结果集中的列名称
//如果设置为 true，那么它会首先选择主键，设置 keyProperty 然后执行插入语句。
//如果设置为 false，那么先执行插入语句，然后是 selectKey 元素
  boolean before();

  Class<?> resultType();//结果的类型

  StatementType statementType() default StatementType.PREPARED;
}
