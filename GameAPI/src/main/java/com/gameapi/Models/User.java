package com.gameapi.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;
    private String email;
    private String password;
    @Builder.Default
    private List<Role> roles = new ArrayList<>() {
        {
            add(Role.ROLE_USER);
        }
    };
}
