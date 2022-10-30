package com.gameapi.Controllers;

import com.gameapi.DTOs.UserDTO;
import com.gameapi.Interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UsersController {

    private final IUserService _service;
    private final ModelMapper _mapper;

    @GetMapping("{id}")
    public Mono<UserDTO> findByid(@PathVariable("id") String id) {
        return this._service.getUserById(id).map(user -> _mapper.map(user, UserDTO.class));
    }


    @PreAuthorize("hasAuthority('PROFESOR')")
    @GetMapping
    public Flux<UserDTO> GetUsers() {
        return this._service.findAllUsers()
                .map(user -> _mapper.map(user, UserDTO.class));
    }
}
