package com.tp.factory;

import com.tp.configuration.RedisConfigurationProperties;
import com.tp.configuration.RedisSingleConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Auther: hequnayu
 * @Date: 2019/4/9 11:29
 * @Description: 单节点构建
 */
@Component
public class SingleJedisPoolFactory {

    @Autowired
    RedisSingleConfigurationProperties redisConfigurationProperties;

    /**
     * 构建jedispool实例
     * @return
     */
    public JedisPool bulid(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        if(StringUtils.isEmpty(redisConfigurationProperties.getHost()) ||
                StringUtils.isEmpty(redisConfigurationProperties.getPort())){
           throw new RuntimeException("主机名跟端口必须配置");
        }
        jedisPoolConfig.setMaxTotal(redisConfigurationProperties.getMaxTotal());
        jedisPoolConfig.setMaxIdle(redisConfigurationProperties.getMaxIdle());
        jedisPoolConfig.setMinIdle(redisConfigurationProperties.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(redisConfigurationProperties.getMaxWait());

        if (!StringUtils.isEmpty(redisConfigurationProperties.getPassword()))
            return new JedisPool(jedisPoolConfig,redisConfigurationProperties.getHost(),redisConfigurationProperties.getPort(),redisConfigurationProperties.getConnectTimeout(),redisConfigurationProperties.getPassword());
        return new JedisPool(jedisPoolConfig,redisConfigurationProperties.getHost(),redisConfigurationProperties.getPort(),redisConfigurationProperties.getConnectTimeout());
    }
}
