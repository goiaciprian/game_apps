//package com.gameapi.Controllers;
//
//import com.gameapi.DTOs.RequestDTO.AuthenticationRequest;
//import com.gameapi.DTOs.RequestDTO.RegisterRequest;
//import com.gameapi.DTOs.ResponseDTO.RegisteredResponseDTO;
//import com.gameapi.Models.User;
//import com.gameapi.Repositories.UserRepository;
//import com.gameapi.Security.JwtTokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.ReactiveAuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Mono;
//
//import javax.validation.Valid;
//import java.util.ArrayList;
//import java.util.Map;
//
//@RestController
//@RequestMapping("auth")
//@RequiredArgsConstructor
//public class AuthController {
//    private final JwtTokenProvider tokenProvider;
//    private final ReactiveAuthenticationManager authenticationManager;
//    private final UserRepository userRepository;
//
//    @PostMapping("login")
//    public Mono<ResponseEntity> login(@Valid @RequestBody Mono<AuthenticationRequest> request) {
//        return request
//                .flatMap(login -> authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()))
//                        .map(tokenProvider::createToken))
//                .map(jwt -> {
//                    HttpHeaders httpHeaders = new HttpHeaders();
//                    httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
//                    var tokeBody = Map.of("id_token", jwt);
//                    return new ResponseEntity<>(tokeBody, httpHeaders, HttpStatus.OK);
//                });
//    }
//
//    @PostMapping("register")
//    public Mono<ResponseEntity> register(@Valid @RequestBody Mono<RegisterRequest> register) {
//        return register.map(registerRequest -> User.builder().email(registerRequest.getUsername()).password(registerRequest.getPassword()).build())
//                .flatMap(userRepository::save)
//                .map(user -> new ResponseEntity(user, HttpStatus.OK));
//    }
//}
