package com.gameapi.Controllers;

import com.gameapi.DTOs.RequestDTO.AuthenticationRequest;
import com.gameapi.DTOs.ResponseDTO.AuthResponseDTO;
import com.gameapi.Models.User;
import com.gameapi.Repositories.UserRepository;
import com.gameapi.Utils.JwtUtil;
import com.gameapi.Utils.PBKDF2Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PBKDF2Encoder _encoder;
    private final JwtUtil _jwtUtil;

    @PostMapping("login")
    public Mono<ResponseEntity<AuthResponseDTO>> login(@Valid @RequestBody AuthenticationRequest request) {
        return userRepository.findByEmail(request.getUsername())
                .filter(user -> _encoder.encode(request.getPassword()).equals(user.getPassword()))
                .map(user -> ResponseEntity.ok(AuthResponseDTO.builder().token(_jwtUtil.generateToken(user)).build()))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @PostMapping("register")
    public Mono<ResponseEntity> register(@Valid @RequestBody AuthenticationRequest register) {
        return userRepository.save(
                        User.builder().email(register.getUsername()).password(_encoder.encode(register.getPassword())).build()
                )
                .flatMap((user) ->{
                    var userDto = AuthResponseDTO.builder().token(_jwtUtil.generateToken(user)).build();
                    var response = ResponseEntity.status(HttpStatus.CREATED).body(userDto);
                    return Mono.just(response);
                });
//                .map((user) -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Object() { final String error = "Username already exists."; }));

    }
}
