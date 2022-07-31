package com.gameapi.Controllers;

import com.gameapi.DTOs.RequestDTO.SubmittedCodeDTO;
import com.gameapi.Models.SubmittedCode;
import com.gameapi.Services.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/code")
public class CodeController {

    private final CodeService _codeService;
    private final Sinks.Many<SubmittedCode> _sinks;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<SubmittedCode> GetStrings() {
        return _codeService.findAll();
    }

    @PostMapping
    public Mono<SubmittedCode> PostString(@RequestBody SubmittedCodeDTO dto) {
        return _codeService.create(new SubmittedCode(dto.getUserId(), dto.getCode()));
    }


    @GetMapping(path = "sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux see() {
        return _sinks.asFlux().map(elem -> ServerSentEvent.builder(elem).build());
    }

}
