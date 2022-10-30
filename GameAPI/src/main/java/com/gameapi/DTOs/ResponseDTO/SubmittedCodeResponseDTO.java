package com.gameapi.DTOs.ResponseDTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubmittedCodeResponseDTO implements Serializable {
    private String id;
    private String createdBy;
    private String code;
    private String testCode;
}
