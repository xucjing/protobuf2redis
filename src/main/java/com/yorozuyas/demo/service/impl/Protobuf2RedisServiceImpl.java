package com.yorozuyas.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yorozuyas.demo.dao.Protobuf2RedisDAO;
import com.yorozuyas.demo.model.DeliverAddress;
import com.yorozuyas.demo.service.Protobuf2RedisService;

@Service
public class Protobuf2RedisServiceImpl implements Protobuf2RedisService {

	@Autowired
	private Protobuf2RedisDAO protobuf2RedisDAO;

	@Override
	@Transactional
	public void newAddress( DeliverAddress addressInfo ) {

		protobuf2RedisDAO.newAddress( addressInfo );
	}

	@Override
	@Transactional(readOnly = true)
	public List<DeliverAddress> fetchAddress( String uid ) {

		return protobuf2RedisDAO.fetchAddress( uid );
	}

	@Override
	@Transactional
	public void modifyAddress( DeliverAddress addressInfo ) {

		protobuf2RedisDAO.modifyAddress( addressInfo );
	}

	@Override
	@Transactional
	public void evictAddress( Long id, String uid ) {

		protobuf2RedisDAO.evictAddress( id, uid );
	}

}
