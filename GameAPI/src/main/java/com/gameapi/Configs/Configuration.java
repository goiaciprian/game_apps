package com.gameapi.Configs;

import com.gameapi.DTOs.RedisDTO.SubmittedCodeRedis;
import com.gameapi.Models.SubmittedCode;
import com.gameapi.Repositories.UserRepository;
import com.gameapi.Security.JwtTokenAuthenticationFilter;
import com.gameapi.Security.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Sinks;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public Sinks.Many<SubmittedCode> getSinks() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }


    @Bean
    public ReactiveRedisTemplate<String, SubmittedCodeRedis> submitterCodeTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisSerializer<SubmittedCodeRedis> serializer = new Jackson2JsonRedisSerializer<>(SubmittedCodeRedis.class);
        RedisSerializationContext<String, SubmittedCodeRedis> serializationContext = RedisSerializationContext.<String, SubmittedCodeRedis>newSerializationContext(RedisSerializer.string())
                .value(serializer)
                .build();
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, serializationContext);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    SecurityWebFilterChain webFilterChain (ServerHttpSecurity http, JwtTokenProvider tokenProvider) {
        return http
                .csrf(it -> it.disable())
                .httpBasic(it -> it.disable())
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(it -> it
                        .pathMatchers(HttpMethod.GET, "/api/code/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/code/**").hasRole("ADMIN")
                        .anyExchange().permitAll()
                )
                .addFilterAt(new JwtTokenAuthenticationFilter(tokenProvider), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(UserRepository userRepository) {
        return (email) -> userRepository.findByEmail(email)
                .map(found -> {
                    System.out.println(found.getEmail());
                    return found;
                })
                .map(u -> User.withUsername(u.getEmail())
                        .password(u.getPassword())
                        .authorities(u.getRoles().toArray(new String[0]))
                        .accountExpired(false)
                        .credentialsExpired(false)
                        .disabled(false)
                        .accountExpired(false)
                        .accountLocked(false)
                        .build()
                );
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

}
