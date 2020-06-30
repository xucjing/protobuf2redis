package com.yorozuyas.demo.dao;

import java.util.List;

import com.yorozuyas.demo.model.DeliverAddress;

public interface Protobuf2RedisDAO {
	
	List<DeliverAddress> newAddress(DeliverAddress addressInfo);

	List<DeliverAddress> fetchAddress( String uid );
	
	List<DeliverAddress> modifyAddress( DeliverAddress addressInfo );

	int evictAddress( Long id, String uid );
}
