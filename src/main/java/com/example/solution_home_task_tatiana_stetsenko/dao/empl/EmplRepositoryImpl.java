package com.example.solution_home_task_tatiana_stetsenko.dao.empl;

import com.example.solution_home_task_tatiana_stetsenko.dto.EmplDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class EmplRepositoryImpl implements EmplRepository{
        Map<String,List<EmplDto>> map = new ConcurrentHashMap<>();

        @Override
        public String addEmpl(String publicId, EmplDto empl) {
            UUID id = UUID.randomUUID();
            empl.setId(id.toString());
            map.computeIfAbsent(publicId,key -> new CopyOnWriteArrayList<>()).add(empl);
            return id.toString();
        }

    @Override
        public List<EmplDto> getAll(String publicId) {
            return map.getOrDefault(publicId,List.of());
        }

        @Override
        public void deleteEmpl(String publicId, String emplId) {
            map.get(publicId).removeIf(empl -> empl.getId().equals(emplId));
        }
    }

