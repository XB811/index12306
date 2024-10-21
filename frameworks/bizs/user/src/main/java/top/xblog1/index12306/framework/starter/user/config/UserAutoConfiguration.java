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

package top.xblog1.index12306.framework.starter.user.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import top.xblog1.index12306.framework.starter.user.core.UserTransmitFilter;

import static top.xblog1.index12306.framework.starter.bases.constant.FilterOrderConstant.USER_TRANSMIT_FILTER_ORDER;

/**
 * @FileName UserAutoConfiguration
 * @Description 用户配置类自动装配
 */
//根据当前应用程序是否是一个Web应用程序来决定是否配置用户登录类
@ConditionalOnWebApplication
public class UserAutoConfiguration {

    /**
    * 将用户配置过滤器添加到过滤器队列中
    * @return FilterRegistrationBean<UserTransmitFilter>
    * @Date 2024/10/21 15:37
    */
    @Bean
    public FilterRegistrationBean<UserTransmitFilter> globalUserTransmitFilter(){
        FilterRegistrationBean<UserTransmitFilter> registration = new FilterRegistrationBean<>();
        //将UserTransmitFilter添加进filter队列
        registration.setFilter(new UserTransmitFilter());
        //设置过滤器响应路径
        registration.addUrlPatterns("/*");
        //设置过滤器的优先级
        registration.setOrder(USER_TRANSMIT_FILTER_ORDER);
        return registration;
    }
}
