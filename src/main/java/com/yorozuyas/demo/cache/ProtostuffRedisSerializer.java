package com.yorozuyas.demo.cache;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 使用Protostuff对value进行编解码。
 */
public final class ProtostuffRedisSerializer implements RedisSerializer<Object> {

	final Schema<CacheValueWrapper> schema = RuntimeSchema.getSchema( CacheValueWrapper.class );

	@Override
	public byte[] serialize( Object cvw ) throws SerializationException {

		try {
			return encode( cvw );
		}
		catch ( RuntimeException e ) {
			throw new SerializationException( "Could not encode CacheValueWrapper to Protostuff: "
					+ e.getMessage(), e );
		}
	}

	@Override
	public Object deserialize( byte[] bytes ) throws SerializationException {

		try {
			return decode( bytes );
		}
		catch ( RuntimeException e ) {
			throw new SerializationException( "Could not decode Protostuff to CacheValueWrapper: "
					+ e.getMessage(), e );
		}
	}

	// do serialize
	public byte[] encode( Object value ) {

		final LinkedBuffer buffer = LinkedBuffer.allocate();
		return ProtostuffIOUtil.toByteArray( new CacheValueWrapper( value ), schema, buffer );
	}

	// do deserialize
	public Object decode( byte[] bytes ) {

		CacheValueWrapper wrapper = new CacheValueWrapper();

		ProtostuffIOUtil.mergeFrom( bytes, wrapper, schema );
		return wrapper.getData();
	}

	@AllArgsConstructor
	@NoArgsConstructor
	public static class CacheValueWrapper {

		@Getter
		private Object data;
	}
}
