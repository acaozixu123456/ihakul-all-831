package com.xiaoai.ms.redis;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.JedisPubSub;

/** redis操作接口
 * Created by 曹子谞 on 2017/7/13.
 * proName:ihakul_server_7.12
 */
public interface JedisClient {
    //hash操作
    String set(String key, String value);
    String get(String key);
    Boolean exists(String key);
    Long expire(String key, int seconds);
    Long ttl(String key);
    Long incr(String key);
    Long hset(String key, String field, String value);
    String hget(String key, String field);
    Long hdel(String key, String... field);
    Map<String, String> hgetAll(String key);

    //list操作
    List<String> lrange(String key, int begin, int end);
    Long lpush(String key,String value);
    Long rpush(String key,String value);
    Long ldel(String key);
    String lindex(String key,Integer index);
    
  //publish\sublish
    public void subScribe(JedisPubSub jedisPubSub,String ... channels) throws Exception;  
    
    public Long publish(String channels,String msg);
    
    public Long publish(String channels,String msg,String obj);
}
