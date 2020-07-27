package com.yorozuyas.demo.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.yorozuyas.demo.dao.Protobuf2RedisDAO;
import com.yorozuyas.demo.model.DeliverAddress;

@Component
public class Protobuf2RedisDAOImpl implements Protobuf2RedisDAO {

	/**
	 * name space
	 */
	private static final String NEW_ADDRESS = "com.yorozuyas.demo.dao.Protobuf2RedisMapper.newAddress";
	private static final String FETCH_ADDRESS = "com.yorozuyas.demo.dao.Protobuf2RedisMapper.fetchAddress";
	private static final String MODIFY_ADDRESS = "com.yorozuyas.demo.dao.Protobuf2RedisMapper.modifyAddress";
	private static final String EVICT_ADDRESS = "com.yorozuyas.demo.dao.Protobuf2RedisMapper.evictAddress";

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	@Cacheable(cacheNames = "address", key = "'address:' + #addressInfo.getUid()", unless = "#result.isEmpty()")
	public List<DeliverAddress> newAddress( DeliverAddress addressInfo ) {

		sqlSessionTemplate.insert( NEW_ADDRESS, addressInfo );
		return sqlSessionTemplate.selectList( FETCH_ADDRESS, addressInfo.getUid() );
	}

	@Override
	@Cacheable(cacheNames = "address", key = "'address:' + #uid", unless = "#result.isEmpty()")
	public List<DeliverAddress> fetchAddress( String uid ) {

		return sqlSessionTemplate.selectList( FETCH_ADDRESS, uid );
	}

	@Override
	@CachePut(cacheNames = "address", key = "'address:' + #addressInfo.getUid()", unless = "#result.isEmpty()")
	public List<DeliverAddress> modifyAddress( DeliverAddress addressInfo ) {

		sqlSessionTemplate.update( MODIFY_ADDRESS, addressInfo );
		return sqlSessionTemplate.selectList( FETCH_ADDRESS, addressInfo.getUid() );
	}

	@Override
	@CacheEvict(cacheNames = "address", key = "'address:' + #uid")
	public int evictAddress( Long id, String uid ) {

		final Map<String, Object> map = new HashMap<String, Object>( 2 );
		map.put( "id", id );
		map.put( "uid", uid );

		return sqlSessionTemplate.delete( EVICT_ADDRESS, map );
	}

}
