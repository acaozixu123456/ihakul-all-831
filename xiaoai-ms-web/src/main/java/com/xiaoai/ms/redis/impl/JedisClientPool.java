package com.xiaoai.ms.redis.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PreDestroy;

/*import com.xiaoai.mina.service.session.DefaultSessionManager;*/

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.xiaoai.ms.redis.JedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by 曹子谞 on 2017/7/13. proName:ihakul_server_7.12 单机版实现类
 */
public class JedisClientPool implements JedisClient {
	@Autowired
	private JedisPool jedisPool;

	@Value("${redis.password}")
	private String redis_pass;

	private static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	 
	private static Logger logger = Logger.getLogger(JedisClientPool.class);
	
	private static final AtomicInteger THREAD_COUNT = new AtomicInteger(0);
	 
	public static ExecutorService getCachedthreadpool() {
		return cachedThreadPool;
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		String result = jedis.set(key, value);
		jedis.close();
		return result;
	}

	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		String result = jedis.get(key);
		jedis.close();
		return result;
	}

	@Override
	public Boolean exists(String key) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		Boolean result = jedis.exists(key);
		jedis.close();
		return result;
	}

	@Override
	public Long expire(String key, int seconds) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		Long result = jedis.expire(key, seconds);
		jedis.close();
		return result;
	}

	@Override
	public Long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	@Override
	public Long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	@Override
	public Long hset(String key, String field, String value) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		Long result = jedis.hset(key, field, value);
		jedis.close();
		return result;
	}

	@Override
	public String hget(String key, String field) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		String result = jedis.hget(key, field);
		jedis.close();
		return result;
	}

	@Override
	public Long hdel(String key, String... field) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		Long result = jedis.hdel(key, field);
		jedis.close();
		return result;
	}

	@Override
	public List<String> lrange(String key, int begin, int end) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		List<String> list = jedis.lrange(key, begin, end);
		jedis.close();
		return list;
	}

	@Override
	public Long lpush(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		Long lpush = jedis.lpush(key, value);
		jedis.close();
		return lpush;
	}

	@Override
	public Long rpush(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		Long rpush = jedis.rpush(key, value);
		jedis.close();
		return rpush;
	}

	@Override
	public Long ldel(String key) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		Long del = jedis.del(key);
		jedis.close();
		return del;
	}

	@Override
	public String lindex(String key, Integer index) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		String lindex = jedis.lindex(key, index);
		jedis.close();
		return lindex;
	}

	@Override
	public void subScribe(final JedisPubSub jedisPubSub, final String... channels) throws Exception {
		Thread thread = null;
		try {
			jedisSub jedisSub = new jedisSub(jedisPubSub,channels);
			thread = new Thread(jedisSub);
			thread.setDaemon(true);
			//cachedThreadPool.execute(thread);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(thread!=null){
				thread.interrupt();
			}
		}
	}

	@Override
	public Long publish(String channels, String msg) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		Long publish = jedis.publish(channels, msg);
		jedis.close();
		return publish;
		
	}

	@Override
	public Long publish(String channels, String msg, String obj) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		Long publish = jedis.publish(channels, msg);
		jedis.close();
		return publish;
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_pass);
		Map<String, String> hgetAll = jedis.hgetAll(key);
		jedis.close();
		return hgetAll;
	}

	/**
	 * 关闭线程池
	 */
	@PreDestroy
	public void close(){

		cachedThreadPool.shutdownNow();
	}
	
	/**
	 * 
	 */
	/*@PostConstruct
	public void init_sub(){
		logger.info("jedis订阅初始化！");
		JedisClient jedisClient = ContextHolder.getBean(JedisClient.class);
		try {
			jedisClient.subScribe(new DefaultSessionManager(),"haku_mina_session");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * 订阅
	 * @Description
	 * @author Administrator
	 * @Data 2017-7-27下午4:21:27
	 */
	class jedisSub implements Runnable{
		private JedisPubSub jedisPubSub;
		private String[] channels;
		
		
		public jedisSub(JedisPubSub jedisPubSub, String[] channels) {
			super();
			this.jedisPubSub = jedisPubSub;
			this.channels = channels;
		}

		@Override
		public void run() {
			Thread.currentThread().setName("JedisClientPool:"+THREAD_COUNT.incrementAndGet());
			Jedis jedis = null;
			try {
				jedis = jedisPool.getResource();
				jedis.auth(redis_pass);
				jedis.subscribe(jedisPubSub, channels);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(jedis!=null){
					jedis.close();
				}
				//如果抛出异常则再次设置中断请求
                //Thread.currentThread().interrupt();
				jedis.quit();
				jedis.disconnect();
				logger.info("jedis.quit()");
			}	
		}
		
	}
}
