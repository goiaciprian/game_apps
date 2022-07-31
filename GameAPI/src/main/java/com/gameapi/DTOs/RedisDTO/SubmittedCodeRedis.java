package com.gameapi.DTOs.RedisDTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubmittedCodeRedis implements Serializable {
    private String id;
    private String userId;
    private String code;
    private boolean successfulExecuted = false;
}
