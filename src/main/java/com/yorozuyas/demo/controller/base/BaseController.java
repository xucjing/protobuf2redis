package com.yorozuyas.demo.controller.base;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.yorozuyas.demo.enums.Code;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class BaseController {

	/**
	 * 全局异常处理 {@link org.springframework.web.client.HttpClientErrorException}
	 * 
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleClientRequestException( Exception ex ) {

		// 400 Bad Request
		if ( ex instanceof HttpClientErrorException.BadRequest ) {

			final HttpClientErrorException.BadRequest bad = ( HttpClientErrorException.BadRequest ) ex;

			log.info( "Request: fail, HttpStatus: 400, ErrorMessage: {}", bad.getStatusText() );

			return ResponseEntity
					.badRequest()
					.body( BaseResponseEntity.builder()
							.code( Code.BAD.getCode() )
							.errMsg( bad.getStatusText() )
							.build() );
		}

		if ( ex instanceof HttpMessageNotReadableException ) {

			final HttpMessageNotReadableException bad = ( HttpMessageNotReadableException ) ex;

			log.info( "Request: fail, HttpStatus: 400, ErrorMessage: {}", bad.getMessage() );

			return ResponseEntity
					.badRequest()
					.body( BaseResponseEntity.builder()
							.code( Code.BAD.getCode() )
							.errMsg( "No parameter available, what you missed?" )
							.build() );
		}

		if ( ex instanceof MethodArgumentTypeMismatchException ) {

			final MethodArgumentTypeMismatchException bad = ( MethodArgumentTypeMismatchException ) ex;

			log.info( "Request: fail, HttpStatus: 400, ErrorMessage: {}",
					String.format( "'%s' %s", bad.getName(), bad.getMessage() ) );

			return ResponseEntity
					.badRequest()
					.body( BaseResponseEntity.builder()
							.code( Code.BAD.getCode() )
							.errMsg( "'" + bad.getName() + "' type not available, what you missed?" )
							.build() );
		}

		// 405 Method Not Allowed
		if ( ex instanceof HttpRequestMethodNotSupportedException ) {

			final HttpRequestMethodNotSupportedException ns = ( HttpRequestMethodNotSupportedException ) ex;

			// format error message
			final StringBuilder builder = new StringBuilder( ns.getMessage() + ", only supported [ " );
			final String[] supportedMethods = ns.getSupportedMethods();
			for ( int i = 0; i < supportedMethods.length; i++ ) {
				if ( i == 0 ) {
					builder.append( supportedMethods[i] );
					continue;
				}
				builder.append( ", " + supportedMethods[i] );
			}
			builder.append( " ]" );
			String errMsg = builder.toString();

			log.info( "Request: fail, HttpStatus: 405, ErrorMessage: {}", errMsg );

			return ResponseEntity
					.status( HttpStatus.METHOD_NOT_ALLOWED )
					.body( BaseResponseEntity.builder()
							.code( Code.NOT_ALLOWED.getCode() )
							.errMsg( errMsg )
							.build() );
		}

		// 415 Unsupported Media Type
		if ( ex instanceof HttpMediaTypeNotSupportedException ) {

			final HttpMediaTypeNotSupportedException ns = ( HttpMediaTypeNotSupportedException ) ex;

			// format error message
			final StringBuilder builder = new StringBuilder( ns.getMessage() + ", only supported [ " );
			final List<MediaType> supportedMediaTypes = ns.getSupportedMediaTypes();
			for ( int i = 0; i < supportedMediaTypes.size(); i++ ) {
				if ( i == 0 ) {
					builder.append( supportedMediaTypes.get( i ) );
					continue;
				}
				builder.append( ", " + supportedMediaTypes.get( i ) );
			}
			builder.append( " ]" );
			String errMsg = builder.toString();

			log.info( "Request: fail, HttpStatus: 415, ErrorMessage: {}", errMsg );

			return ResponseEntity
					.status( HttpStatus.UNSUPPORTED_MEDIA_TYPE )
					.body( BaseResponseEntity.builder()
							.code( Code.UNSUPPORTED_MEDIA_TYPE.getCode() )
							.errMsg( errMsg )
							.build() );
		}

		// Others Exception
		log.error( "Un-Excepted Error occured.", ex );

		return ResponseEntity
				.status( HttpStatus.INTERNAL_SERVER_ERROR )
				.body( BaseResponseEntity.builder()
						.code( Code.INTERNAL_SERVER_ERROR.getCode() )
						.errMsg( HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() )
						.build() );
	}

}
