package com.gameapi.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameapi.DTOs.WSResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Slf4j
@Qualifier("code")
@RequiredArgsConstructor
public class WebSocketHandlerService implements WebSocketHandler {

    private final Sinks.Many<WSResponse<String>> _codeSink;

    private final ObjectMapper _serializer;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(
                _codeSink.asFlux().map(val -> {
                    try {
                        return session.textMessage(_serializer.writeValueAsString(val));
                    } catch (JsonProcessingException e) {
                        log.error("Error serializer: ", e);
                        return session.textMessage("Error serializer");
                    }
                })
        );
    }

}
