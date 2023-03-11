package com.gameapi.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Group implements Serializable {
    @Id
    private String id;
    private String creatorId;

    private String name;

    @Builder.Default
    private ArrayList<String> users = new ArrayList<>();
}
