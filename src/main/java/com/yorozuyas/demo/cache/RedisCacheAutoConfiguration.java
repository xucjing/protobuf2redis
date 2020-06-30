package com.yorozuyas.demo.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
@AutoConfigureAfter(CacheAutoConfiguration.class)
@ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis", matchIfMissing = false)
public class RedisCacheAutoConfiguration {

	@Autowired
	private CacheProperties cacheProperties;

	/**
	 * Custom {@link org.springframework.data.redis.cache.RedisCacheConfiguration}
	 * 
	 * 
	 * @see org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
	 */
	@Bean
	public RedisCacheConfiguration determineConfiguration() {

		final Redis redisProperties = cacheProperties.getRedis();
		
		// Only open TTL, others close.
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				.disableCachingNullValues()
				.disableKeyPrefix()
				.serializeValuesWith( SerializationPair.fromSerializer( new ProtostuffRedisSerializer() ) );

		if ( redisProperties.getTimeToLive() != null ) {
			config = config.entryTtl( redisProperties.getTimeToLive() );
		}

		return config;
	}
}
