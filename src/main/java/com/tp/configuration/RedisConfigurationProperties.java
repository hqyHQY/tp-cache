package com.tp.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: hequnayu
 * @Date: 2019/4/12 16:23
 * @Description: redis 公共配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "tp.redis")
public class RedisConfigurationProperties {

    /**
     * redis模式：单节点(默认)、主从、集群
     */
    private String model = "single";

    /**
     * 连接超时时间
     */
    private int connectTimeout = 10000;

    /**
     * 获取数据超时时间
     */
    private int soTimeout = 10000;

    /**
     * Redis访问用户名
     */
    private String userName;

    /**
     * Redis访问密码
     */
    private String password;

    /**
     * 一个pool最多可分配多少个jedis实例
     */
    private int maxTotal = 500;

    /**
     * 一个pool最多可以有多少个空闲的jedis实例
     */
    private int maxIdle = 100;

    /**
     * 一个pool至少要有多少个空闲的jedis实例
     */
    private int minIdle = 0;

    /**
     * 当连接池资源耗尽时，调用者最大阻塞的时间，超时将跑出异常。单位，毫秒数;默认为-1.表示永不超时.
     */
    private int maxWait = 15000;

    /**
     * pool中无可用jedis时是否阻塞
     */
    private boolean blockWhenExhausted = true;

}
