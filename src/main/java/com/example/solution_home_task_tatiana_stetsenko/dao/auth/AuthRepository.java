package com.example.solution_home_task_tatiana_stetsenko.dao.auth;

import com.example.solution_home_task_tatiana_stetsenko.document.AuthDoc;

public interface AuthRepository {
    void addUser(String username, String password);
    AuthDoc getUserByUsername(String username);
}
