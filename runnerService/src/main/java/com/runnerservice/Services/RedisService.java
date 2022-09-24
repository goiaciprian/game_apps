package com.runnerservice.Services;

import com.runnerservice.Models.RunnerException;
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
    private final ReactiveRedisTemplate<String, RunnerException> _exceptionTemplate;

    @Value("${redis.channel.listen}")
    private String listenChannel;
    @Value("${redis.channel.publish}")
    private String publishChannel;
    @Value("${redis.channel.exception}")
    private String exceptionChannel;

    public Flux<SubmittedCode> getSubmittedCode() {
        return _template.listenTo(ChannelTopic.of(listenChannel)).map(ReactiveSubscription.Message::getMessage);
    }

    public void publish(SubmittedCode code) {
        _template.convertAndSend(publishChannel, code).subscribe();
    }
    public void publish(RunnerException exception) { _exceptionTemplate.convertAndSend(exceptionChannel, exception).subscribe(); }
}
