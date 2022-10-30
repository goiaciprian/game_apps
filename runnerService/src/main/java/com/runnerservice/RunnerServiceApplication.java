package com.runnerservice;

import com.runnerservice.Exceptions.MessageReceivedIsNullException;
import com.runnerservice.Models.RunnerException;
import com.runnerservice.Services.CodeRunnerService;
import com.runnerservice.Services.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class RunnerServiceApplication {

    private final RedisService _redisService;
    private final CodeRunnerService _codeRunnerService;

    @Value("${testproject.path}")
    private String testProjectPath;

    @Value("${shell.command}")
    private String shellCommand;

    @Value("${shell.argument}")
    private String shellArgument;

    public static void main(String[] args) { SpringApplication.run(RunnerServiceApplication.class, args); }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        while (true) {
            try {
                var submittedCode = _redisService.getSubmittedCode().blockFirst();
                if(submittedCode == null) {
                    throw new MessageReceivedIsNullException();
                }

                _codeRunnerService.writeToMain(submittedCode.getCode());
                _codeRunnerService.writeToTest(submittedCode.getTestCode());

                var processBuilder = new ProcessBuilder(shellCommand, shellArgument, "mvn", "test");
                processBuilder.directory(new File(testProjectPath));

                var process = processBuilder.start();

                var errorBuffer = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                var outputList = new BufferedReader(new InputStreamReader(process.getInputStream())).lines().toList();

                var relevantOutput = outputList.stream().skip(outputList.size() - 12).collect(Collectors.joining());
                var relavantError = errorBuffer.lines().collect(Collectors.joining());

                submittedCode.setStdout(relevantOutput);
                submittedCode.setStderr(relavantError);

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
