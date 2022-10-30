package com.gameapi.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document
@RequiredArgsConstructor
@NoArgsConstructor

public class SubmittedCode implements Serializable {

    @Id
    private String id;

    @NonNull
    private String createdBy;
    @NonNull
    private String code;
    @NonNull
    private String testCode;
    private boolean successfulExecuted = false;

    private String stdout;
    private String stderr;
}
