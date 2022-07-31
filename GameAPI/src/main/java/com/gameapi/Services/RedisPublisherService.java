package com.gameapi.Services;

import com.gameapi.DTOs.RedisDTO.SubmittedCodeRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisherService {

    private final ReactiveRedisTemplate<String, SubmittedCodeRedis> _redisTemplate;

    @Value("${redis.channel}")
    private String channel;

    public void sendToRunner(SubmittedCodeRedis code) {
         var statusCode = this._redisTemplate.convertAndSend(channel, code);
        statusCode.subscribe(System.out::println);
    }

}
