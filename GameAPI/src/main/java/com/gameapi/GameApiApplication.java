package com.gameapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class GameApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(GameApiApplication.class, args);
    }

}
