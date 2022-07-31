package com.gameapi.Models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@RequiredArgsConstructor
public class SubmittedCode {

    @Id
    private String id;

    @NonNull
    private String userId;
    @NonNull
    private String code;
    private boolean successfulExecuted = false;

}
