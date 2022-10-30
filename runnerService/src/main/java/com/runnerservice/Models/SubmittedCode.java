package com.runnerservice.Models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubmittedCode {
    private String id;
    private String createdBy;
    private String code;
    private String testCode;
    private boolean successfulExecuted = false;
    private String stdout;
    private String stderr;
}
