package com.gameapi.DTOs.RequestDTO;

import lombok.Data;

@Data
public class SubmittedCodeDTO {
    private String createdBy;
    private String code;
    private String testCode;
}
