package com.example.solution_home_task_tatiana_stetsenko.dao.empl;

import com.example.solution_home_task_tatiana_stetsenko.dto.EmplDto;

import java.util.List;

public interface EmplRepository {
    String addEmpl(String publicId, EmplDto empl);
    List<EmplDto> getAll(String publicId);
    void deleteEmpl(String publiId, String emplId);
}
