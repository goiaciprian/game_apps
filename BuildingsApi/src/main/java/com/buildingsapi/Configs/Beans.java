package com.buildingsapi.Configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;

@Configuration
public class Beans
{
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

//    @Bean
//    public ReactiveHashOperations<String, String, User> getHashOps(LettuceConnectionFactory lettuceConnectionFactory) {
//        RedisSerializer<User> serializer = new Jackson2JsonRedisSerializer<>(User.class);
//        RedisSerializationContext<String, User> serializationContext = RedisSerializationContext.<String, User>newSerializationContext(RedisSerializer.string())
//                .key(new StringRedisSerializer())
//                .value(serializer)
//                .hashKey(new StringRedisSerializer())
//                .hashValue(new GenericJackson2JsonRedisSerializer())
//                .build();
//        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, serializationContext).opsForHash(serializationContext);
//    }
}
