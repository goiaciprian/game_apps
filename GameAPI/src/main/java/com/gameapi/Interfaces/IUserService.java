package com.gameapi.Interfaces;

import com.gameapi.Models.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<User> findByEmail(String email);
    Mono<User> getUserById(String idUser);
    Mono<User> saveIfNotExists(User user);

    Flux<User> findAllUsers();
}
