package com.gameapi.Configs;

import com.gameapi.Utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthentificationManager implements ReactiveAuthenticationManager {

    private final JwtUtil _jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username = _jwtUtil.getUsernameFromToken(authToken);
        return Mono.just(_jwtUtil.validateToken(authToken))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.empty())
                .map(valid -> {
                    Claims claims = _jwtUtil.getAllClaimsFromToken(authToken);
                    List<String> rolesMap = claims.get("role", List.class);
                    var authorities = rolesMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                    return new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                        );
                });
    }
}
