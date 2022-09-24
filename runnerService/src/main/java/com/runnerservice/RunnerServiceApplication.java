package com.runnerservice;

import com.runnerservice.Exceptions.MessageReceivedIsNullException;
import com.runnerservice.Models.RunnerException;
import com.runnerservice.Services.CodeRunnerService;
import com.runnerservice.Services.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.serializer.SerializationException;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class RunnerServiceApplication {

    private final RedisService _redisService;
    private final CodeRunnerService _codeRunnerService;

    public static void main(String[] args) { SpringApplication.run(RunnerServiceApplication.class, args); }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        while (true) {
            try {
                var submittedCode = _redisService.getSubmittedCode().blockFirst();
                if(submittedCode == null) {
                    throw new MessageReceivedIsNullException();
                }
                submittedCode.setSuccessfulExecuted(true);
                _codeRunnerService.test();
                _redisService.publish(submittedCode);
            }
            catch (SerializationException ser) {
                log.error("SerializationException converting to SubmittedCode: {}\nCause: {}\n Class: {}", ser.getMessage(), ser.getCause().getMessage(), RunnerServiceApplication.class);
                _redisService.publish(RunnerException.builder().exceptionLog(ser.toString()).build());
            }
            catch (Exception e) {
                log.error("Error: ", e);
                _redisService.publish(RunnerException.builder().exceptionLog(e.toString()).build());
            }
        }
    }
}
