package com.yorozuyas.demo.cache.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.yorozuyas.demo.cache.annotation.RedissonLock;

@Aspect
@Component
public class RedissonLockAspect {

	@Autowired
	public RedissonClient redissonClient;

	@Around(value = "@annotation(com.yorozuyas.demo.cache.annotation.RedissonLock)&&@annotation(lock)")
	public Object doAround( ProceedingJoinPoint jp, RedissonLock lock ) throws Throwable {

		if ( !StringUtils.hasText( lock.key() ) ) {
			throw new IllegalArgumentException( "invalid parameter of redissonLock key: " + lock.key() );
		}

		RLock rLock = redissonClient.getLock( lock.key() );
		try {
			boolean tryLock = rLock.tryLock( lock.waitTime(), lock.leaseTime(), lock.timeUnit() );
			if ( !tryLock ) {
				throw new RuntimeException(
						StringUtils.isEmpty( lock.errMsg() ) ? "trylock failure of key: " + lock.key() : lock.errMsg() );
			}
			return jp.proceed();
		}
		finally {
			rLock.unlock();
		}
	}
}
