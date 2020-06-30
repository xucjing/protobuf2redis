package com.yorozuyas.demo.service;

import java.util.List;

import com.yorozuyas.demo.model.DeliverAddress;

public interface Protobuf2RedisService {

	void newAddress( DeliverAddress addressInfo );

	List<DeliverAddress> fetchAddress( String uid );

	void modifyAddress( DeliverAddress addressInfo );

	void evictAddress( Long id, String uid );

}
