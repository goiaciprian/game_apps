package com.buildingsapi.Configs;

import com.buildingsapi.Utils.JwtUtil;
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
        String token = authentication.getCredentials().toString();
        String userId = _jwtUtil.getUserIdFromToken(token);
        return Mono.just(_jwtUtil.validateToken(token))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.empty())
                .map(valid -> {
                    Claims claims = _jwtUtil.getAllClaimsFromToken(token);
                    List<String> rolesMap = claims.get("roles", List.class);
                    var authorities = rolesMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                    return new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            authorities
                    );
                });
    }
}
