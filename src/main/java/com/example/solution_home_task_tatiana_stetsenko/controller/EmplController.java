package com.example.solution_home_task_tatiana_stetsenko.controller;

import com.example.solution_home_task_tatiana_stetsenko.dao.empl.EmplRepository;
import com.example.solution_home_task_tatiana_stetsenko.dto.EmplDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmplController {
    @Autowired
    EmplRepository repo;

    @PostMapping
    String addEmpl(@RequestBody EmplDto empl, Principal principal) {
        return repo.addEmpl(principal.getName(), empl);
    }

    @GetMapping
    List<EmplDto> getAll(Principal principal){
        return  repo.getAll(principal.getName());
    }
}
