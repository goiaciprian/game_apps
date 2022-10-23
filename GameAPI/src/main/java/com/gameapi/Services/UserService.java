package com.gameapi.Services;

import com.gameapi.Exceptions.UserAlredyExistsException;
import com.gameapi.Interfaces.IUserService;
import com.gameapi.Models.User;
import com.gameapi.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "cache", name = "enabled", havingValue = "false")
public class UserService implements IUserService {

    private final UserRepository _userRepo;

    public Mono<User> findByEmail(String email) {
        return _userRepo.findByEmail(email);
    }

    public Mono<User> saveIfNotExists(User user)  {
        return _userRepo.findByEmail(user.getEmail())
                .flatMap(__ -> Mono.error(new UserAlredyExistsException("User alredy exists.")))
                .switchIfEmpty(Mono.defer(() -> _userRepo.save(user)))
                .cast(User.class);
    }

    public Flux<User> findAllUsers() {
        return this._userRepo.findAll();
    }

    public Mono<User> getUserById(String idUser) {
        return this._userRepo.findById(idUser);
    }

}
