package com.buildingsapi.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/test")
public class TestController {

    @GetMapping
    public Mono<String> userTest() {
        return Mono.just("Yes, you are logged in.");
    }

    @GetMapping("prof")
    @PreAuthorize("hasAnyAuthority('PROFESOR')")
    public Mono<String> profTest() {
        return Mono.just("Yes, you are logged in and you're a teacher.");
    }

}
