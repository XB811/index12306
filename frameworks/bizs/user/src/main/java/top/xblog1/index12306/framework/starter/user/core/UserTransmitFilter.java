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

package top.xblog1.index12306.framework.starter.user.core;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import top.xblog1.index12306.framework.starter.bases.constant.UserConstant;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * @FileName UserTransmitFilter
 * @Description 过滤器，拦截http请求将用户信息存入
 * 用户上下文 UserContext，以便于下流业务直接获取信息
 */

public class UserTransmitFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //依次解析获取userId，userName，RealName
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String userId = request.getHeader(UserConstant.USER_ID_KEY);
        if(StringUtils.hasText(userId)){
            String userName = request.getHeader(UserConstant.USER_NAME_KEY);
            String realName = request.getHeader(UserConstant.REAL_NAME_KEY);
            //更改userName和realName的编码格式
            if(StringUtils.hasText(userName)){
                userName = URLDecoder.decode(userName, "UTF-8");
            }
            if(StringUtils.hasText(realName)){
                realName = URLDecoder.decode(realName, "UTF-8");
            }
            //存入UserContext
            UserInfoDTO userInfoDTO =UserInfoDTO.builder()
                    .userId(userId)
                    .username(userName)
                    .realName(realName)
                    .build();
            UserContext.setUser(userInfoDTO);
        }
        try {
            //FilterChain 接口的 doFilter 方法用于通知 Web 容器把请求交给 Filter 链中的下一个 Filter 去处理
            //执行下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            //在经过 进入Filter -> 执行业务代码 -> 离开过滤器后
            //再次回到本层过滤器时，删除UserContext中所存储的用户信息
            UserContext.removeUser();
        }
    }
}
