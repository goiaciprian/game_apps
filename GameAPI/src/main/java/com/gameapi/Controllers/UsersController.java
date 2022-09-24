package com.gameapi.Controllers;

import com.gameapi.DTOs.RequestDTO.AuthenticationRequest;
import com.gameapi.Models.User;
import com.gameapi.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UsersController {

    private final UserRepository _repository;

    @PostMapping
    public Mono<User> PostUser(@Valid @RequestBody AuthenticationRequest user) {
        return _repository.save(User.builder().email(user.getUsername()).password(user.getPassword()).build());
    }

    @GetMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Flux<User> GerUsers() {
        return _repository.findAll();
    }
}
