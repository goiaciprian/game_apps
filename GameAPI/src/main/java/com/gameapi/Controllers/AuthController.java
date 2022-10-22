package com.gameapi.Controllers;

import com.gameapi.CacheServices.UserCacheService;
import com.gameapi.DTOs.RequestDTO.AuthenticationRequest;
import com.gameapi.DTOs.ResponseDTO.AuthResponseDTO;
import com.gameapi.Exceptions.UserAlredyExistsException;
import com.gameapi.Interfaces.IUserService;
import com.gameapi.Models.User;
import com.gameapi.Repositories.UserRepository;
import com.gameapi.Services.UserService;
import com.gameapi.Utils.JwtUtil;
import com.gameapi.Utils.PBKDF2Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserCacheService userService;
    private final PBKDF2Encoder _encoder;
    private final JwtUtil _jwtUtil;

    @PostMapping("login")
    public Mono<ResponseEntity<AuthResponseDTO>> login(@Valid @RequestBody AuthenticationRequest request) {
        return userService.findByEmail(request.getEmail())
                .filter(user -> _encoder.encode(request.getPassword()).equals(user.getPassword()))
                .map(user -> ResponseEntity.ok(AuthResponseDTO.builder().token(_jwtUtil.generateToken(user)).username(request.getEmail()).build()))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @PostMapping("register")
    public Mono<ResponseEntity<AuthResponseDTO>> register(@Valid @RequestBody AuthenticationRequest register, @RequestParam(defaultValue = "false", name = "prof") boolean prof) {
        var usr = User.builder().email(register.getEmail()).password(_encoder.encode(register.getPassword())).build();
        usr.setRole(prof);
        return userService.saveIfNotExists(usr)
                .flatMap(user -> {
                    var userDto = AuthResponseDTO.builder().token(_jwtUtil.generateToken(user)).username(user.getUsername()).build();
                    var response = ResponseEntity.status(HttpStatus.CREATED).body(userDto);
                    return Mono.just(response);
                });
    }
}
