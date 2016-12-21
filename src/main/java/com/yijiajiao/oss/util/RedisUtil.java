package com.yijiajiao.oss.util;


import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	public static Logger log = (Logger) LoggerFactory.getLogger(RedisUtil.class);
	public static final int webexpire = Config.getInt("redis.webexpire");
	public static final int appexpire = Config.getInt("redis.appexpire");
	private static String   redisIp = Config.getString("redis.ip");
	private static int redisPort = Config.getInt("redis.port");
	private static int active = Config.getInt("redis.maxactive");
	private static int idle = Config.getInt("redis.maxidle");
	private static int wait = Config.getInt("redis.maxwait");
	public static JedisPool pool;
    /**
     * 初始化Redis连接池
     */
    private static JedisPool getJedisPool(){
    	if(pool == null ){
        	JedisPoolConfig config = new JedisPoolConfig();
          //  config.setMaxActive(active);
            config.setMaxIdle(idle);
           // config.setMaxWait(wait);
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, redisIp,redisPort);
            log.info("jedis连接池初始化完成！");
    	}
    	return pool;
    }
    /** 
     * 返还到连接池 
     *  
     * @param pool  
     * @param redis 
     */  
    public static void returnResource(JedisPool pool, Jedis redis) {  
        if (redis != null) {  
            pool.returnResource(redis);  
        }  
    }
    
	public static void putRedis(String key,String value){
        JedisPool pool = null;  
        Jedis jedis = null;
		try {
			log.info("key="+key+",value="+value);
			pool =getJedisPool();
			jedis = pool.getResource();
			jedis.set(key,value);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(pool, jedis);
		}
	}
	
	public static void expire(String openId,int expire){
        JedisPool pool = null;  
        Jedis jedis = null;
		try {
			pool = getJedisPool();
			jedis=pool.getResource();
			jedis.expire(openId, expire);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(pool, jedis);
		}
	}
	/**
	 *@description	查询key剩余时间
	 *@date 2016-7-4
	 *@return
	 */
	public static long ttl(String key){
        JedisPool pool = null;  
        Jedis jedis = null;
		if("".equals(key) || key==null) return -1;
		try {
			pool = getJedisPool();
			jedis=pool.getResource();
			Long ttl = jedis.ttl(key);
			return ttl;
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(pool, jedis);
		}
	}
	
	/**
	 *@description	判断键是否存在，不存在返回false
	 *@date 2016-7-4
	 *@return
	 */
	  public static boolean exist(String token) {
	        JedisPool pool = null;  
	        Jedis jedis = null;
		    boolean flag = false;
		    if ("".equals(token) || token == null) {
		      return flag;
		    }
		    try {
		    	pool = getJedisPool();
				jedis = pool.getResource();
				flag = jedis.exists(token.getBytes());
				return flag;
			} catch (Exception e) {
				pool.returnBrokenResource(jedis);
				throw e;
			}finally{
				returnResource(pool, jedis);
			}
	}
	  
	  
	  public static String getValue(String token) {
	        JedisPool pool = null;  
	        Jedis jedis = null;
		    String value = "";
		    if ("".equals(token) || token == null) {
		      return value;
		    }
		    try {
		    	pool = getJedisPool();
				jedis = pool.getResource();
				value = jedis.get(token);
				return value;
			} catch (Exception e) {
				pool.returnBrokenResource(jedis);
				throw e;
			}finally{
				returnResource(pool, jedis);
			}
	}
	  
}
