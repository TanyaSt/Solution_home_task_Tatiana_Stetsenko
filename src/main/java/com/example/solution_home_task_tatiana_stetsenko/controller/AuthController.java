package com.example.solution_home_task_tatiana_stetsenko.controller;

import com.example.solution_home_task_tatiana_stetsenko.dao.auth.AuthRepository;
import com.example.solution_home_task_tatiana_stetsenko.dto.AuthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    AuthRepository repo;

    @PostMapping("registration")
    void registration(@RequestBody AuthDto auth){
        repo.addUser(auth.getUsername(),auth.getPassword());
    }
}

