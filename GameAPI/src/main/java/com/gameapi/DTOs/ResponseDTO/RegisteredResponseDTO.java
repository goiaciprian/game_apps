package com.gameapi.DTOs.ResponseDTO;

import com.gameapi.Models.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisteredResponseDTO {

    private User createdUser;
    private String token;
}
