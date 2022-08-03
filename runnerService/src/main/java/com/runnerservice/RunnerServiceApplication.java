package com.runnerservice;

import com.runnerservice.Services.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@RequiredArgsConstructor
public class RunnerServiceApplication {

    private final RedisService _redisService;

    public static void main(String[] args) { SpringApplication.run(RunnerServiceApplication.class, args); }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        while (true) {
            var submittedCode = _redisService.getSubmittedCode().blockFirst();
            System.out.println(submittedCode.getCode());
            submittedCode.setSuccessfulExecuted(true);
            _redisService.publish(submittedCode);
        }
    }
}
