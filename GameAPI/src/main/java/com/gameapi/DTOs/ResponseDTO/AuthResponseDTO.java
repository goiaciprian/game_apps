package com.gameapi.DTOs.ResponseDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO {
    private String token;
}
