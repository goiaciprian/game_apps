package com.gameapi.DTOs.RedisDTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubmittedCodeRedis implements Serializable {
    private String id;
    private String createdBy;
    private String code;
    private String testCode;
    private String stdout;
    private String stderr;
    private boolean successfulExecuted = false;
}
