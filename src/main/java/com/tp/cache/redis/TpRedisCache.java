package com.tp.cache.redis;

import com.alibaba.fastjson.JSON;
import com.tp.factory.SingleJedisPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;

/**
 * @Auther: hequnayu
 * @Date: 2019/4/3 18:53
 * @Description: 默认提供jedis客户端实现
 */
@Primary
@Service
public class TpRedisCache implements RedisCache{

    private Logger logger = LoggerFactory.getLogger(TpRedisCache.class);

    @Autowired
    private SingleJedisPoolFactory poolFactory;

    public JedisPool getJedisPool(){
        JedisPool jedisPool = poolFactory.bulid();
        if(jedisPool == null){
            logger.error("获取redis连接池失败");
            throw new RuntimeException("can not get JedisPool!!!");
        }
        return jedisPool;
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean exists(String key){
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        }catch (Exception e){
            logger.error("TpRedisCache method error ,method:{},key:{}","exists",key);
            e.printStackTrace();
        }finally {
            close(jedis);
        }
        return false;
    }

    /**
     * 设置key的过期时间
     * @param key
     * @param expire
     */
    public void expire(String key,int expire){
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.expire(key,expire);
        }catch (Exception e){
            logger.error("TpRedisCache method error ,method:{},key:{}","expire",key);
            e.printStackTrace();
        }finally {
            close(jedis);
        }
    }

    /**
     * 获取key的过期时间
     * @param key
     * @return
     */
    public Long ttl(String key){
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.ttl(key);
        }catch (Exception e){
            logger.error("TpRedisCache method error ,method:{},key:{}","ttl",key);
            e.printStackTrace();
        }finally {
            close(jedis);
        }
        return null;
    }

    /**
     * 移除key的生存时间限制
     * @param key
     */
    public void persist(String key){
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.persist(key);
        }catch (Exception e){
            logger.error("TpRedisCache method error ,method:{},key:{}","persist",key);
            e.printStackTrace();
        }finally {
            close(jedis);
        }
    }


    /**
     * 删除对应的key
     * @param key
     */
    public void del(String key){
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.del(key);
        }catch (Exception e){
            logger.error("TpRedisCache method error ,method:{},key:{}","del",key);
            e.printStackTrace();
        }finally {
            close(jedis);
        }
    }

    /**
     * 批量删除对应的key
     * @param keys
     */
    public void del(String[] keys){
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.del(keys);
        }catch (Exception e){
            logger.error("TpRedisCache method error ,method:{},key:{}","del[]",keys);
            e.printStackTrace();
        }finally {
            close(jedis);
        }
    }

    /**
     * 获取key的string值
     * @param key
     * @return
     */
    public String get(String key){
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String data = jedis.get(key);
            return data;
        }catch (Exception e){
            logger.error("TpRedisCache method error ,method:{},key:{}","get",key);
            e.printStackTrace();
        }finally {
            close(jedis);
        }
        return null;
    }

    /**
     * 将key的value转换成相关对象或数据类型输出
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String key,Class clazz){
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = null;
        T result = null;
        try{
            jedis = jedisPool.getResource();
            String data = jedis.get(key);
            return this.conversionToObject(data,clazz);
        }catch (Exception e){
            logger.error("TpRedisCache method error ,method:{},key:{}","get",key);
            e.printStackTrace();
        }finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 增加（覆盖数据），默认永不过期
     * @param key
     * @param value
     */
    public void set(String key,Object value){
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.set(key, JSON.toJSONString(value));
        }catch (Exception e){
            logger.error("TpRedisCache method error ,method:{},key:{}","set",key);
            e.printStackTrace();
        }finally {
            close(jedis);
        }
    }

    /**
     * 不覆盖增加数据
     * @param key
     * @param value
     */
    public void setnx(String key,Object value){
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.setnx(key, JSON.toJSONString(value));
        }catch (Exception e){
            logger.error("TpRedisCache method error ,method:{},key:{}","setnx",key);
            e.printStackTrace();
        }finally {
            close(jedis);
        }
    }

    /**
     *
     * @param key
     * @param value
     * @param expire 过期时间，单位：秒
     */
    public void setex(String key,Object value,int expire){
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.setex(key, expire,JSON.toJSONString(value));
        }catch (Exception e){
            logger.error("TpRedisCache method error ,method:{},key:{}","setex",key);
            e.printStackTrace();
        }finally {
            close(jedis);
        }
    }

    /**
     * 在key对应value扩展字符串
     * @param key
     * @param value
     */
    public void append(String key,String value){
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.append(key, value);
        }catch (Exception e){
            logger.error("TpRedisCache method error ,method:{},key:{}","append",key);
            e.printStackTrace();
        }finally {
            close(jedis);
        }
    }

    /**
     * 获取key对应value第start 到 end 的字符
     * @param key
     * @param start
     * @param end
     * @return
     */
    public String getrange(String key,int start,int end){
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.getrange(key, start, end);
        }catch (Exception e){
            logger.error("TpRedisCache method error ,method:{},key:{}","getrange",key);
            e.printStackTrace();
        }finally {
           close(jedis);
        }
        return null;
    }




    /**
     * 结果转换输出
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T conversionToObject(String data,Class clazz){
        if(StringUtils.isEmpty(data))
            return null;
        else
            data = data.trim();
        if(clazz == int.class || clazz == Integer.class){
            Integer result = Integer.valueOf(data);
            return (T) result;
        }else if(clazz == long.class || clazz == Long.class){
            Long result = Long.valueOf(data);
            return (T) result;
        }else if(clazz == float.class || clazz == Float.class){
            Float result = Float.valueOf(data);
            return (T) result;
        }else if(clazz == double.class || clazz == Double.class){
            Double result = Double.valueOf(data);
            return (T) result;
        }else if(clazz == short.class || clazz == Short.class){
            Short result = Short.valueOf(data);
            return (T) result;
        }else if(clazz == boolean.class || clazz == Boolean.class){
            Boolean result = Boolean.valueOf(data);
            return (T) result;
        }else if(clazz == char.class || clazz == Character.class){
            char[] chars = data.toCharArray();
            return (T) chars;
        }else if(clazz == byte.class || clazz == Byte.class){
            Byte result = Byte.valueOf(data);
            return (T) result;
        }else if(clazz == String.class){
            return (T) data;
        }else{
            return (T) JSON.parseObject(data,clazz);
        }
    }

    /**
     * 关闭jedis
     * @param jedis
     */
    public void close(Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }

}