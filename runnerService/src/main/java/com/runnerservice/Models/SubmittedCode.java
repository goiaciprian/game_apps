package com.runnerservice.Models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubmittedCode {
    private String id;
    private String userId;
    private String code;
    private String testCode;
    private boolean successfulExecuted = false;
}
