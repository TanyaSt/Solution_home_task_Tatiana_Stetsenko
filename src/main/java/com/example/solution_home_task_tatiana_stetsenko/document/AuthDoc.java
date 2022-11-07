package com.example.solution_home_task_tatiana_stetsenko.document;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AuthDoc {
    String username;
    String password;
    String publicId;
    String[] roles;
}
