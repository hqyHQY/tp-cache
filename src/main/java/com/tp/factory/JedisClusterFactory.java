package com.tp.factory;

import com.tp.configuration.RedisClusterConfigurationProperties;
import com.tp.execption.RedisCacheExecption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Auther: hequnayu
 * @Date: 2019/4/12 16:53
 * @Description:
 */
@Component
@Configuration
public class JedisClusterFactory {

    @Autowired
    private RedisClusterConfigurationProperties clusterConfigurationProperties;

    @Bean
    public JedisCluster bulid(){
        String hostPort = clusterConfigurationProperties.getHostPort();
        if(StringUtils.isEmpty(hostPort)){
            throw new RedisCacheExecption("The cluster mode must have a host and port!!");
        }
        String[] hostPortArr = hostPort.split(",");
        if(hostPortArr.length <= 0)
            throw new RedisCacheExecption("The cluster mode must have more than one instance!!");
        Set<HostAndPort> hostAndPorts = Arrays.stream(hostPortArr).map(hp -> {
            String host = hp.split(":")[0].trim();
            Integer port = Integer.parseInt(hp.split(":")[1].trim());
            HostAndPort hostAndPort = new HostAndPort(host, port);
            return hostAndPort;
        }).collect(Collectors.toSet());
        if (hostAndPorts ==null || hostAndPorts.size() <= 0)
            throw new RedisCacheExecption("Cluster mode nodes do not meet the requirements");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(clusterConfigurationProperties.getMaxTotal());
        jedisPoolConfig.setMinIdle(clusterConfigurationProperties.getMaxIdle());
        jedisPoolConfig.setMinIdle(clusterConfigurationProperties.getMinIdle());
        jedisPoolConfig.setBlockWhenExhausted(clusterConfigurationProperties.isBlockWhenExhausted());
        if (StringUtils.isEmpty(clusterConfigurationProperties.getPassword()))
            return new JedisCluster(hostAndPorts,clusterConfigurationProperties.getConnectTimeout(),
                    clusterConfigurationProperties.getSoTimeout(),1,jedisPoolConfig);
        return new JedisCluster(hostAndPorts,clusterConfigurationProperties.getConnectTimeout(),
                clusterConfigurationProperties.getSoTimeout(),1,clusterConfigurationProperties.getPassword(),jedisPoolConfig);
    }
}
