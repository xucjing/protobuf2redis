package com.yorozuyas.demo.cache;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson分布式锁配置
 */

@Configuration
public class RedissonConfiguration {

	@Autowired
	private RedisProperties redisProperties;

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		config.useSingleServer()
				.setAddress( "redis://" + redisProperties.getHost() + ":" + redisProperties.getPort() )
				.setPassword( redisProperties.getPassword() )
				.setTcpNoDelay( true );

		return Redisson.create( config );
	}
}
