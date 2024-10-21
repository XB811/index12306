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

package top.xblog1.index12306.framework.starter.user.toolkit;

import com.alibaba.fastjson2.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import top.xblog1.index12306.framework.starter.user.core.UserInfoDTO;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static top.xblog1.index12306.framework.starter.bases.constant.UserConstant.*;

/**
 * @FileName JWTUtil
 * @Description jwt令牌工具类
 */
@Slf4j
public final class JWTUtil {

    //过期时间
    private static final long EXPIRATION = 86400L;
    //jwt令牌的前缀
    public static final String TOKEN_PREFIX = "Bearer ";
    //JWT的签发者
    public static final String ISS = "index12306";
    //秘钥
    public static final String SECRET = "SecretKey039245678901232039487623456783092349288901402967890140939827";

    public static String generateAccessToken(UserInfoDTO userInfo){
        //从userInfo中提取信息并封装进map中
        Map<String, Object> customerUserMap = new HashMap<String, Object>();
        customerUserMap.put(USER_ID_KEY,userInfo.getUserId());
        customerUserMap.put(USER_NAME_KEY,userInfo.getUsername());
        customerUserMap.put(REAL_NAME_KEY,userInfo.getRealName());
        //构建jwt令牌
        String jwtToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECRET)//加密算法，加密密钥
                .setIssuedAt(new Date())//签发时间
                .setIssuer(ISS)//签发者
                .setSubject(JSON.toJSONString(customerUserMap))//jwt令牌的内容
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION*1000))//过期时间
                .compact();
        return TOKEN_PREFIX+jwtToken;
    }
    public static UserInfoDTO parseJwtToken(String jwtToken){
        //令牌不能为空
        if(StringUtils.hasText(jwtToken)){
            //删除令牌前缀
            String actualJwtToken = jwtToken.replace(TOKEN_PREFIX, "");
            try {
                //解析
                Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(actualJwtToken).getBody();
                //判断过期时间
                Date expiration = claims.getExpiration();
                if(expiration.after(new Date())){
                    //如果没有过期，就解析并返回
                    String subject = claims.getSubject();
                    return JSON.parseObject(subject, UserInfoDTO.class);
                }
            }catch (ExpiredJwtException ignored){
            }catch (Exception ex){
                log.error("JWT Token解析失败，请检查", ex);
            }
            
        }
        //如果用户未登录或者令牌已经过期，返回null
        return null;
    }
}
