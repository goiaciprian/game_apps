package com.gameapi.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    public String id;
    public String creatorId;
    public String name;
    public ArrayList<String> usersId;
}
