package com.gameapi.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameapi.DTOs.RedisDTO.SubmittedCodeRedis;
import com.gameapi.DTOs.WSResponse;
import com.gameapi.Models.RunnerException;
import com.gameapi.Models.SubmittedCode;
import com.gameapi.Models.WSResponseTypes;
import com.gameapi.Repositories.IRunnerExceptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPublisherService {

    private final ReactiveRedisTemplate<String, SubmittedCodeRedis> _redisTemplate;
    private final ReactiveRedisTemplate<String, RunnerException> _exceptionTemplate;
    private final IRunnerExceptionRepository _exceptionRepository;
    private final Sinks.Many<WSResponse<String>> _sinks;
    private final ObjectMapper _stringMapper;
    private final ModelMapper _mapper;
    @Value("${redis.channel.publish}")
    private String publishChannel;
    @Value("${redis.channel.listen}")
    private String listenChannel;
    @Value("${redis.channel.exception}")
    private String exceptionChannel;
    public void sendToRunner(SubmittedCodeRedis code) {
         this._redisTemplate.convertAndSend(publishChannel, code).subscribe(listenerNumber -> log.info("[REDIS] Number of receivers: {}", listenerNumber));
    }

    public synchronized void listenToRunnerService() {
        this._exceptionTemplate.listenTo(new ChannelTopic(exceptionChannel)).subscribe(message -> {
            _exceptionRepository.insert(message.getMessage()).subscribe(exc ->
            {
                try {
                    _sinks.tryEmitNext(WSResponse.<String>builder().type(WSResponseTypes.EXCEPTION).content(_stringMapper.writeValueAsString(exc)).build());
                } catch (JsonProcessingException e) {
                    log.error("Error emitting exception", e);
                }
            });
        });

        this._redisTemplate.listenTo(new ChannelTopic(listenChannel)).subscribe(message -> {
            var code = _mapper.map(message.getMessage(), SubmittedCode.class);
            try {
                _sinks.tryEmitNext(WSResponse.<String>builder().type(WSResponseTypes.CODE).content(_stringMapper.writeValueAsString(code)).build());
            } catch (JsonProcessingException e) {
                log.error("Error emitting submitted code", e);
            }
        });

    }
}
