package com.lyb.olinemusicserver.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;
import java.lang.reflect.Type;

/**
 * FastJSON 2.x 的 Redis 序列化器实现
 * 用于替代默认的 JDK 序列化方式，提供更好的性能和可读性
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    private final Type type;

    public FastJsonRedisSerializer(Class<T> clazz) {
        this.type = clazz;
    }

    public FastJsonRedisSerializer(TypeReference<T> typeReference) {
        this.type = typeReference.getType();
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        try {
            return JSON.toJSONBytes(t);
        } catch (Exception ex) {
            throw new SerializationException("FastJSON 序列化失败", ex);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return JSON.parseObject(new String(bytes, StandardCharsets.UTF_8), type);
        } catch (Exception ex) {
            throw new SerializationException("FastJSON 反序列化失败", ex);
        }
    }
}
