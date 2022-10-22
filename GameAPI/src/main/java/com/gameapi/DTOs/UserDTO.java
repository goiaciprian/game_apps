package com.gameapi.DTOs;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDTO implements Serializable {
    private String id;
    private String email;
    private List<String> roles;

}
