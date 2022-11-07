package com.example.solution_home_task_tatiana_stetsenko.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class TokenDto {
    String token;
    String type;
}
