/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.xblog1.index12306.framework.starter.bases.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 应用初始化后置处理器，防止Spring事件被多次执行
 * 公众号：马丁玩编程，回复：加群，添加马哥微信（备注：12306）获取项目资料
 */
/**
* 原理：ContextRefreshedEvent： 当应用程序上下文（ApplicationContext）初始化或刷新完成后触发。
 * 这通常发生在应用程序启动过程中，并且表示应用程序已准备好接收请求和执行业务逻辑。可以使用该事件来执行一些初始化操作，
 * 例如加载缓存数据、启动后台任务等。
 *
 * 但是，我们不需要在应用程序上下文（ApplicationContext）刷新时进行spring刷新事件
 * 所有通过本文件配置禁用刷新时的spring事件初始化
* @param null
* @return
* @Date 2024/10/21 13:34
*/
@RequiredArgsConstructor
public class ApplicationContentPostProcessor implements ApplicationListener<ApplicationReadyEvent> {
    // 注解导入applicationContext
    private final ApplicationContext applicationContext;

    /**
     * 执行标识，确保Spring事件 {@link ApplicationReadyEvent} 有且执行一次
     * 默认未初始化
     */
    private final AtomicBoolean executeOnlyOnce = new AtomicBoolean(false);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        //如果spring事件不是从false(未初始)到true(true) 那么直接返回，不进行初始化
        if (!executeOnlyOnce.compareAndSet(false, true)) {
            return;
        }
        //调用ApplicationInitializingEvent对spring事件进行初始化
        applicationContext.publishEvent(new ApplicationInitializingEvent(this));
    }
}
