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

package top.xblog1.index12306.framework.starter.bases;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
/**
 * @FileName Singleton
 * @Description 单例对象容器
 *
 *提供一种单例的访问模式，单例的优点如下：
 * 1. 全局访问：单例对象可以在应用程序的任何地方被访问，而不需要传递对象的引用。这样可以方便地共享对象的状态和功能，简化了对象之间的通信和协作。
 * 2. 节省资源：由于只有一个对象实例存在，可以减少重复创建对象的开销。在需要频繁创建和销毁对象的情况下，单例对象可以显著节省系统资源，提高性能。
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Singleton {
    //使用哈希表存储key和bean对象的关系
    private static final ConcurrentHashMap<String, Object> SINGLE_OBJECT_POOL = new ConcurrentHashMap();

    /**
     * 根据 key 获取单例对象
     */
    public static <T> T get(String key) {
        Object result = SINGLE_OBJECT_POOL.get(key);
        return result == null ? null : (T) result;
    }

    /**
     * 根据 key 获取单例对象
     *
     * <p> 为空时，通过 supplier 构建单例对象并放入容器
     */
    //如果单例对象不存在，则创建单例对象并存入哈希表中，并返回
    public static <T> T get(String key, Supplier<T> supplier) {
        Object result = SINGLE_OBJECT_POOL.get(key);
        if (result == null && (result = supplier.get()) != null) {
            SINGLE_OBJECT_POOL.put(key, result);
        }
        return result != null ? (T) result : null;
    }

    /**
     * 对象放入容器
     */
    //对象放入容器时，参数只有Object，由该方法自定义key
    public static void put(Object value) {
        put(value.getClass().getName(), value);
    }

    /**
     * 对象放入容器
     */
    //根据key，value存入单例对象
    public static void put(String key, Object value) {
        SINGLE_OBJECT_POOL.put(key, value);
    }
}

