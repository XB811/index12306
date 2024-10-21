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

package top.xblog1.index12306.framework.starter.bases.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import top.xblog1.index12306.framework.starter.bases.ApplicationContextHolder;
import top.xblog1.index12306.framework.starter.bases.init.ApplicationContentPostProcessor;
import top.xblog1.index12306.framework.starter.bases.safa.FastJsonSafeMode;

/**
 * @FileName ApplicationBaseAutoConfiguration
 * @Description 应用基础自动装配
 */

public class ApplicationBaseAutoConfiguration {
    /**
    * 应用程序上下文（ApplicationContext）控制器
     * 扩展控制器的各种功能
    * @return ApplicationContextHolder
    * @Date 2024/10/21 13:30
    */
    @Bean
    @ConditionalOnMissingBean
    public ApplicationContextHolder congoApplicationContextHolder() {
        return new ApplicationContextHolder();
    }
    /**
    * spring事件初始化参数配置，保证spring事件只初始化一次
    * @param applicationContext
    * @return ApplicationContentPostProcessor
    * @Date 2024/10/21 13:31
    */
    @Bean
    @ConditionalOnMissingBean
    public ApplicationContentPostProcessor congoApplicationContentPostProcessor(ApplicationContext applicationContext) {
        return new ApplicationContentPostProcessor(applicationContext);
    }
    /**
    * 规定打开json转object的安全模式
    * @return FastJsonSafeMode
    * @Date 2024/10/21 13:21
    */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "framework.fastjson.safa-mode", havingValue = "true")
    public FastJsonSafeMode congoFastJsonSafeMode() {
        return new FastJsonSafeMode();
    }
}
