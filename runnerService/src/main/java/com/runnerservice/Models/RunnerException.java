package com.runnerservice.Models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RunnerException {
    @Builder.Default
    private Date exceptionDate = new Date();
    private String exceptionLog;
}
