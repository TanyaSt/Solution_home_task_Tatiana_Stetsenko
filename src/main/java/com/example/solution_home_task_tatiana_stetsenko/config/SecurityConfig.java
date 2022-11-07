package com.example.solution_home_task_tatiana_stetsenko.config;

import com.example.solution_home_task_tatiana_stetsenko.config.filter.JWTAuthenticationFilter;
import com.example.solution_home_task_tatiana_stetsenko.config.filter.JWTAuthorizationFilter;
import com.example.solution_home_task_tatiana_stetsenko.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Bean
    AuthenticationManager authManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    Filter authenticationFilter() throws Exception {
        return new JWTAuthenticationFilter(authenticationManager());
    }

    @Bean
    Filter authorizationFilter() throws Exception {
        return new JWTAuthorizationFilter(authenticationManager());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .cors().and().csrf().disable()
                .authorizeRequests()

                .mvcMatchers(HttpMethod.POST, "/auth/registration").permitAll()
                .mvcMatchers(HttpMethod.POST, "/auth/login").permitAll()

                .mvcMatchers(HttpMethod.GET, "/employees**").hasRole("USER")
                .mvcMatchers(HttpMethod.POST, "/employees**").hasRole("USER")
                .mvcMatchers(HttpMethod.PUT, "/employees**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/employees**").hasRole("ADMIN")

                .anyRequest().denyAll()
                .and()
                .addFilter(authenticationFilter())
                .addFilter(authorizationFilter())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}