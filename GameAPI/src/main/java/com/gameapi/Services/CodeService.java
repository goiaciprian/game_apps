package com.gameapi.Services;

import com.gameapi.DTOs.RedisDTO.SubmittedCodeRedis;
import com.gameapi.Models.SubmittedCode;
import com.gameapi.Repositories.ISubmittedCodeRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class CodeService {

    private final ISubmittedCodeRepo _codeRepo;
    private final Sinks.Many<SubmittedCode> _sinks;

    private final RedisPublisherService _redisService;

    private final ModelMapper _mapper;

    public Mono<SubmittedCode> create(SubmittedCode code) {
        return _codeRepo.save(code).map(elem -> {
            _redisService.sendToRunner(_mapper.map(elem, SubmittedCodeRedis.class));
            _sinks.tryEmitNext(elem);
            return elem;
        });
    };
    public Flux<SubmittedCode> findAll() {
        return _codeRepo.findAll();
    };
}
