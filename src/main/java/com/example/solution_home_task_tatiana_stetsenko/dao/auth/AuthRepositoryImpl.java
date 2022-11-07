package com.example.solution_home_task_tatiana_stetsenko.dao.auth;

import com.example.solution_home_task_tatiana_stetsenko.document.AuthDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AuthRepositoryImpl implements AuthRepository {
    Map<String, AuthDoc> map = new ConcurrentHashMap<>();

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void addUser(String username, String password) {
        if (map.putIfAbsent(username, AuthDoc.builder()
                .username(username)
                .roles(new String[]{"ROLE_USER"})
                .password(encoder.encode(password))
                .publicId(UUID.randomUUID().toString())
                .build()) != null) {
            throw new RuntimeException("User already exists");
        }
    }

    @Override
    public AuthDoc getUserByUsername(String username) {
        return map.get(username);
    }
}
