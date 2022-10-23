package com.gameapi.Configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameapi.CacheServices.UserCacheService;
import com.gameapi.DTOs.RedisDTO.SubmittedCodeRedis;
import com.gameapi.DTOs.WSResponse;
import com.gameapi.Models.RunnerException;
import com.gameapi.Models.User;
import com.gameapi.Repositories.UserRepository;
import com.gameapi.Services.WebSocketHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class Configurations {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;


    @Bean
    public Sinks.Many<WSResponse<String>> getSinks() {
        return Sinks.many().multicast().directBestEffort();
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
    public ReactiveRedisTemplate<String, RunnerException> exceptionTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisSerializer<RunnerException> serializer = new Jackson2JsonRedisSerializer<>(RunnerException.class);
        RedisSerializationContext<String, RunnerException> serializationContext = RedisSerializationContext.<String, RunnerException>newSerializationContext(RedisSerializer.string())
                .value(serializer)
                .build();
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, serializationContext);
    }

    @Bean
    public ReactiveHashOperations<String, String, User> getHashOps(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisSerializer<User> serializer = new Jackson2JsonRedisSerializer<>(User.class);
        RedisSerializationContext<String, User> serializationContext = RedisSerializationContext.<String, User>newSerializationContext(RedisSerializer.string())
                .key(new StringRedisSerializer())
                .value(serializer)
                .hashKey(new StringRedisSerializer())
                .hashValue(new GenericJackson2JsonRedisSerializer())
                .build();
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, serializationContext).opsForHash(serializationContext);
    }

    @Bean
    public UserCacheService getUserCacheService(UserRepository _usrRepo, ReactiveHashOperations<String, String, User> _hashOps ) {
        return new UserCacheService(_usrRepo, _hashOps);
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    };
    @Bean
    public ObjectMapper serializer() {
        return new ObjectMapper();
    }
    @Bean
    public WebSocketHandlerService webSocketHandle(Sinks.Many<WSResponse<String>> sink, ObjectMapper serializer) {
        return new WebSocketHandlerService(sink, serializer);
    }
    @Bean
    public HandlerMapping handlerMapping(WebSocketHandlerService handle) {
        Map<String, WebSocketHandlerService> map = new HashMap<>();
        map.put("/api/code/sse", handle);

        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(map);
        mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return mapping;
    }
    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter(WebSocketService service) {
        return new WebSocketHandlerAdapter(service);
    }
    @Bean
    public WebSocketService webSocketService() {
        return new HandshakeWebSocketService(new ReactorNettyRequestUpgradeStrategy());
    }
}
