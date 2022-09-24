package com.runnerservice.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CodeRunnerService {

    @Value("${testproject.path}")
    private String testProjectpath;

    public void test() {
      log.info("{}", testProjectpath);
    }
}
