package com.xiaoai.ms.redis.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.xiaoai.ms.redis.JedisClient;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by 曹子谞 on 2017/7/13.
 * proName:ihakul_server_7.12
 * 集群版实现类
 */
public class JedisClientCluster implements JedisClient {

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public String set(String key, String value) {
        return jedisCluster.set(key, value);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public Boolean exists(String key) {
        return jedisCluster.exists(key);
    }

    @Override
    public Long expire(String key, int seconds) {
        return jedisCluster.expire(key, seconds);
    }

    @Override
    public Long ttl(String key) {
        return jedisCluster.ttl(key);
    }

    @Override
    public Long incr(String key) {
        return jedisCluster.incr(key);
    }

    @Override
    public Long hset(String key, String field, String value) {
        return jedisCluster.hset(key, field, value);
    }

    @Override
    public String hget(String key, String field) {
        return jedisCluster.hget(key, field);
    }

    @Override
    public Long hdel(String key, String... field) {
        return jedisCluster.hdel(key, field);
    }

    @Override
    public List<String> lrange(String key, int begin, int end) {
        return null;
    }

    @Override
    public Long lpush(String key, String value) {
        return null;
    }

    @Override
    public Long rpush(String key, String value) {
        return null;
    }

    @Override
    public Long ldel(String key) {
        return null;
    }

    @Override
    public String lindex(String key, Integer index) {
        return null;
    }

	@Override
	public void subScribe(JedisPubSub jedisPubSub, String... channels) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long publish(String channels, String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long publish(String channels, String msg, String obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		// TODO Auto-generated method stub
		return null;
	}



}
