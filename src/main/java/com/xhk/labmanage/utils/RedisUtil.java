package com.xhk.labmanage.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * create by xhk on 18/3/4
 */
public class RedisUtil {
    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    private static JedisPool jedisPool;

    public RedisUtil(JedisPool jedisPool) {
        RedisUtil.jedisPool = jedisPool;
    }

    public RedisUtil() {
    }

    private static Jedis getJedis(){
        return jedisPool.getResource();
    }

    private static void closeJedis(Jedis jedis){
        try{
            if(jedis != null){
                jedis.close();
            }
        }catch (Exception e){
            logger.error("closeJedis error",e);
        }

    }
    /**
     * 从缓存得到value
     * @param key
     * @return
     */
    public static String getValue(String key){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            return jedis.get(key);
        }catch (Exception e){
            logger.error("getValue error",e);
            return null;
        }finally {
            closeJedis(jedis);
        }
    }

    public static String setValue(String key, String value, Integer expireTime){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            return jedis.setex(key, expireTime, value);
        }catch (Exception e){
            logger.error("setValue error",e);
            return null;
        }finally {
            closeJedis(jedis);
        }
    }

    public static String setValue(String key, String value){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            return jedis.set(key, value);
        }catch (Exception e){
            logger.error("setValue error",e);
            return null;
        }finally {
            closeJedis(jedis);
        }
    }

    public static boolean delValue(String key){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            jedis.del(key);
            return true;
        }catch (Exception e){
            logger.error("delValue error",e);
            return false;
        }finally {
            closeJedis(jedis);
        }
    }
}
