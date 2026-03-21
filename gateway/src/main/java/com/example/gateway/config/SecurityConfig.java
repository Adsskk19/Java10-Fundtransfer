package com.example.gateway.config;

import com.example.gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/auth/**").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtAuthenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    public AuthenticationWebFilter jwtAuthenticationWebFilter() {
        ReactiveAuthenticationManager authManager = authentication -> {
            String token = authentication.getCredentials().toString();

            if (!jwtUtil.validateToken(token)) {
                return Mono.error(new BadCredentialsException("Invalid JWT"));
            }

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    jwtUtil.getUsernameFromToken(token),
                    null,
                    List.of(new SimpleGrantedAuthority(
                            "ROLE_" + jwtUtil.getRoleFromToken(token)
                    ))
            );

            return Mono.just(auth);
        };

        AuthenticationWebFilter filter = new AuthenticationWebFilter(authManager);

        filter.setServerAuthenticationConverter(exchange -> {
            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                return Mono.just(new UsernamePasswordAuthenticationToken(token, token));
            }

            return Mono.empty();
        });

        return filter;
    }


    // Custom JWT WebFilter
    private WebFilter jwtFilter() {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return chain.filter(exchange);
            }

            String token = authHeader.substring(7);

            if (!jwtUtil.validateToken(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // Create Authentication
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            jwtUtil.getUsernameFromToken(token),
                            null,
                            List.of(new SimpleGrantedAuthority(
                                    "ROLE_" + jwtUtil.getRoleFromToken(token)
                            ))
                    );

            return chain.filter(exchange)
                    .contextWrite(
                            ReactiveSecurityContextHolder.withAuthentication(authentication)
                    );
        };
    }

}
