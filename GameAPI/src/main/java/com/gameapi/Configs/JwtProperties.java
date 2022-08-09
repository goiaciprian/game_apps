package com.gameapi.Configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String secretKey = "`";
    //validity in milliseconds
    private long validityInMs = 3600000; // 1h
}
