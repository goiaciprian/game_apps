package com.runnerservice;

import com.runnerservice.Models.SubmittedCode;
import com.runnerservice.Services.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class RunnerServiceApplication implements CommandLineRunner {

    private final RedisService _redisService;

    public static void main(String[] args) { SpringApplication.run(RunnerServiceApplication.class, args); }

    @Override
    public void run(String... args) {
        while (true) {
            var submittedCode = _redisService.getSubmittedCode().blockFirst();

            System.out.println(submittedCode.getCode());

            submittedCode.setSuccessfulExecuted(true);

            _redisService.publish(submittedCode);
        }
    }
}
