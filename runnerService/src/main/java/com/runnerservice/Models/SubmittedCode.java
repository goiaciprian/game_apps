package com.runnerservice.Models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedCode {
    private String id;
    private String userId;
    private String code;
    private boolean successfulExecuted = false;
}
