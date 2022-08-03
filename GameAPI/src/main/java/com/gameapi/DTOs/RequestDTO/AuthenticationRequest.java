package com.gameapi.DTOs.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    private static final long serialVersionUID = -6986746375915710855L;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
