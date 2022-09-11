package com.gameapi.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Document
@RequiredArgsConstructor
@NoArgsConstructor
public class RunnerException implements Serializable {
    @Id
    private String id;
    @NonNull
    private Date exceptionDate;
    @NonNull
    private String exceptionLog;
}
