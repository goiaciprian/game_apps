package com.runnerservice.Services;

import com.runnerservice.Models.SubmittedCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final ReactiveRedisTemplate<String, SubmittedCode> _template;

    @Value("${redis.channel}")
    private String channel;

    public Flux<SubmittedCode> getSubmittedCode() {
        return _template.listenTo(ChannelTopic.of(channel)).map(ReactiveSubscription.Message::getMessage);
    }

    public void publish(SubmittedCode code) {
        _template.convertAndSend(channel, code).subscribe();
    }
}
