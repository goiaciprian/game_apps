package com.runnerservice.Configurations;

import com.runnerservice.Models.RunnerException;
import com.runnerservice.Models.SubmittedCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory(host ,port);
    }

    @Bean
    public ReactiveRedisTemplate<String, SubmittedCode> submitterCodeTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisSerializer<SubmittedCode> serializer = new Jackson2JsonRedisSerializer<>(SubmittedCode.class);
        RedisSerializationContext<String, SubmittedCode> serializationContext = RedisSerializationContext.<String, SubmittedCode>newSerializationContext(RedisSerializer.string())
                .value(serializer)
                .build();
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, serializationContext);
    }

    @Bean
    public ReactiveRedisTemplate<String, RunnerException> runnerExceptionTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisSerializer<RunnerException> serializer = new Jackson2JsonRedisSerializer<>(RunnerException.class);
        RedisSerializationContext<String, RunnerException> serializationContext = RedisSerializationContext.<String, RunnerException>newSerializationContext(RedisSerializer.string())
                .value(serializer)
                .build();
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, serializationContext);
    }

}
