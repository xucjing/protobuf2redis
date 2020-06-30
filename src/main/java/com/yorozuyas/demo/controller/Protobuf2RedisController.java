package com.yorozuyas.demo.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.mysql.cj.util.StringUtils;
import com.yorozuyas.demo.controller.base.BaseResponseEntity;
import com.yorozuyas.demo.enums.Code;
import com.yorozuyas.demo.model.DeliverAddress;
import com.yorozuyas.demo.service.Protobuf2RedisService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/address")
public class Protobuf2RedisController {

	public static final String URL_NEW_ADDRESS = "/new";
	public static final String URL_FETCH_ADDRESS = "/fetch";
	public static final String URL_MODIFY_ADDRESS = "/modify";
	public static final String URL_EVICT_ADDRESS = "/evict/{id}";

	@Autowired
	private Protobuf2RedisService protobuf2RedisService;

	/**
	 * 模拟用户新增地址 
	 */
	@PostMapping(value = URL_NEW_ADDRESS, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<BaseResponseEntity<String>> newAddress(
			@RequestBody DeliverAddress addressInfo ) {

		log.info( "Request-Method: POST, Request-Path: /new, addressInfo: {}", addressInfo.toString() );

		checkIn( addressInfo );

		protobuf2RedisService.newAddress( addressInfo );

		BaseResponseEntity.BaseResponseEntityBuilder<String> builder = BaseResponseEntity.builder();

		return ResponseEntity.ok()
				.body( builder.code( Code.OK.getCode() )
						.data( "Address added successfully" )
						.build() );

	};

	/**
	 * 模拟用户查询收货地址
	 */
	@GetMapping(value = URL_FETCH_ADDRESS, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<BaseResponseEntity<?>> fetchAddress(
			@RequestParam(value = "uid", required = false) String uid ) {

		log.info( "Request-Method: GET, Request-Path: /fetch, uid: {}", uid );

		// check parameter must not be null
		if ( StringUtils.isNullOrEmpty( uid ) ) {
			throw HttpClientErrorException.create( HttpStatus.BAD_REQUEST, "'uid' must not be null.", null, null,
					StandardCharsets.UTF_8 );
		}

		List<DeliverAddress> data = protobuf2RedisService.fetchAddress( uid );

		BaseResponseEntity.BaseResponseEntityBuilder<Object> entityBuilder = BaseResponseEntity.builder()
				.code( Code.OK.getCode() );
		if ( data.isEmpty() ) {
			return ResponseEntity.ok()
					.body( entityBuilder.data( "No result" ).build() );
		}

		return ResponseEntity.ok()
				.body( entityBuilder.data( data ).build() );
	}

	/**
	 * 模拟用户修改收货地址
	 */
	@PostMapping(value = URL_MODIFY_ADDRESS, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<BaseResponseEntity<String>> modifyAddress(
			@RequestBody DeliverAddress addressInfo ) {

		log.info( "Request-Method: POST, Request-Path: /modify, addressInfo: {}", addressInfo.toString() );

		// check parameter must not be null
		if ( addressInfo.getId() == null )
			throw HttpClientErrorException.create( HttpStatus.BAD_REQUEST, "'id' must not be null.", null, null,
					StandardCharsets.UTF_8 );
		checkIn( addressInfo );

		protobuf2RedisService.modifyAddress( addressInfo );

		BaseResponseEntity.BaseResponseEntityBuilder<String> builder = BaseResponseEntity.builder();

		return ResponseEntity.ok()
				.body( builder.code( Code.OK.getCode() )
						.data( "Address modified successfully" )
						.build() );

	}

	/**
	 * 模拟用户删除收货地址
	 */
	@DeleteMapping(value = URL_EVICT_ADDRESS, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<BaseResponseEntity<String>> evictAddress(
			@PathVariable(value = "id", required = false) Long id,
			@RequestParam(value = "uid", required = false) String uid ) {

		log.info( "Request-Method: DELETE, Request-Path: /evict, id: {}, uid: {}", id, uid );

		// check parameter must not be null
		if ( id == null )
			throw HttpClientErrorException.create( HttpStatus.BAD_REQUEST, "'id' must not be null.", null, null,
					StandardCharsets.UTF_8 );
		if ( StringUtils.isNullOrEmpty( uid ) )
			throw HttpClientErrorException.create( HttpStatus.BAD_REQUEST, "'uid' must not be null.", null, null,
					StandardCharsets.UTF_8 );

		protobuf2RedisService.evictAddress( id, uid );

		BaseResponseEntity.BaseResponseEntityBuilder<String> builder = BaseResponseEntity.builder();

		return ResponseEntity.ok()
				.body( builder.code( Code.OK.getCode() )
						.data( "Address deleted successfully" )
						.build() );
	}

	// check parameter must not be null
	private void checkIn( DeliverAddress addressInfo ) {

		if ( StringUtils.isNullOrEmpty( addressInfo.getUid() ) )
			throw HttpClientErrorException.create( HttpStatus.BAD_REQUEST, "'uid' must not be null.", null, null,
					StandardCharsets.UTF_8 );
		if ( StringUtils.isNullOrEmpty( addressInfo.getName() ) )
			throw HttpClientErrorException.create( HttpStatus.BAD_REQUEST, "'name' must not be null.", null, null,
					StandardCharsets.UTF_8 );
		if ( StringUtils.isNullOrEmpty( addressInfo.getAddress() ) )
			throw HttpClientErrorException.create( HttpStatus.BAD_REQUEST, "'address' must not be null.", null, null,
					StandardCharsets.UTF_8 );
		if ( addressInfo.getMobile() == null )
			throw HttpClientErrorException.create( HttpStatus.BAD_REQUEST, "'mobile' must not be null.", null, null,
					StandardCharsets.UTF_8 );
	}
}
