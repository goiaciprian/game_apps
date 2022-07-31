package com.gameapi.Configs;

import com.gameapi.DTOs.RedisDTO.SubmittedCodeRedis;
import com.gameapi.Models.SubmittedCode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import reactor.core.publisher.Sinks;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public Sinks.Many<SubmittedCode> getSinks() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }


    @Bean
    public ReactiveRedisTemplate<String, SubmittedCodeRedis> submitterCodeTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisSerializer<SubmittedCodeRedis> serializer = new Jackson2JsonRedisSerializer<>(SubmittedCodeRedis.class);
        RedisSerializationContext<String, SubmittedCodeRedis> serializationContext = RedisSerializationContext.<String, SubmittedCodeRedis>newSerializationContext(RedisSerializer.string())
                .value(serializer)
                .build();
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, serializationContext);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    };

}
