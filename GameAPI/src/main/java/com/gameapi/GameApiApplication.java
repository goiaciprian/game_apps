package com.gameapi;

import com.gameapi.Services.RedisPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@RequiredArgsConstructor
@SpringBootApplication()
@ConfigurationPropertiesScan
public class GameApiApplication {

    private final RedisPublisherService _redisService;

    public static void main(String[] args) {
        SpringApplication.run(GameApiApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        _redisService.listenToRunnerService();
    }
}
