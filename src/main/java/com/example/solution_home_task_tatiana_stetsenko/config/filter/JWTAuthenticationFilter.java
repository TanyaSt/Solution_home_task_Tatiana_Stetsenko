package com.example.solution_home_task_tatiana_stetsenko.config.filter;

import com.example.solution_home_task_tatiana_stetsenko.dto.AuthDto;
import com.example.solution_home_task_tatiana_stetsenko.dto.ErrorDto;
import com.example.solution_home_task_tatiana_stetsenko.service.jwt.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    ObjectMapper mapper;

    @Autowired
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = null;
        try {
            AuthDto credentials = new ObjectMapper().readValue(request.getInputStream(), AuthDto.class);
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setHeader("Content-Type", "application/json");

            ErrorDto error = ErrorDto.builder()
                    .error("Wrong format Credentials Object")
                    .build();
            mapper.writeValue(response.getWriter(), error);
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        List<String> authorities = authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String token = jwtService.generatedJwtToken(authResult.getName(), Map.of("roles", authorities));
        response.setHeader("Authorization", "Bearer " + token);
        response.setHeader("Content-Type", "application/json");
        mapper.writeValue(response.getWriter(), TokenDto.builder()
                .token(token)
                .type("Bearer")
                .build());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.addHeader("Content-Type", "application/json");
        ErrorDto error = ErrorDto.builder()
                .error("Username or password incorrect")
                .build();

        mapper.writeValue(response.getWriter(), error);
    }
}

