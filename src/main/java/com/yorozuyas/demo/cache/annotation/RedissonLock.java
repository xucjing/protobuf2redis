package com.yorozuyas.demo.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedissonLock {

	// 要锁住的key
	String key();

	// 锁的过期时间
	long leaseTime() default 3000L;

	// 尝试加锁，最多等待时间
	long waitTime() default 3000L;

	// 获取不到锁时，提示的报错信息
	String errMsg() default "";

	// 时间单位
	TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
