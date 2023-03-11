package com.gameapi.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupRequest {
    public String name;
    public ArrayList<String> userIds = new ArrayList<>();
}
