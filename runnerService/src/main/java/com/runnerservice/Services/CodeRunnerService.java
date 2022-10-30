package com.runnerservice.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class CodeRunnerService {

    @Value("${testproject.path}")
    private String testProjectpath;

    public void test() {
        var mainPath = testProjectpath.concat("/src/main/java/testing/App.java");
        var testPath = testProjectpath.concat("/src/test/java/testing/AppTest.java");
        var main = new File(mainPath);
        var test = new File(testPath);

        log.info("{}\n{}", mainPath, testPath);

        log.info("Main file: {}\nTest file: {}", main.exists(), test.exists());
    }

    public void writeToMain(String code) throws IOException {
        writeToFiles(code, testProjectpath.concat("/src/main/java/testing/App.java"));
    }

    public void writeToTest(String code) throws IOException {
        writeToFiles(code, testProjectpath.concat("/src/test/java/testing/AppTest.java"));

    }

    void writeToFiles(String code, String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        writer.write(code);
        writer.close();
    }

}
