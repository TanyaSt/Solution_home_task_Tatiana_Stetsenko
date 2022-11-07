package com.example.solution_home_task_tatiana_stetsenko;

import com.example.solution_home_task_tatiana_stetsenko.service.jwt.JwtService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        JwtService service = context.getBean(JwtService.class);

//		System.out.println(
//				service
//						.generatedJwtToken(
//								"my-public-id",
//								Map.of("roles", List.of("ROLE_USER"))
//						)
//		);

//		System.out.println(service.tokenToAuthentication("Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibXktcHVibGljLWlkIiwiaXNzIjoiVGVsUmFuIiwiZXhwIjoxNjMzMDIzMjUzLCJpYXQiOjE2MzMwMjI2NTN9.Jp-9Fg5r5Cko1ziYEl3ksEA80r9ZU6_2M3_Ax_9wGDmFzQoMEpaT_didsJ0UrGUKqxRN5-nPyia-PuOCWjA-RA"));
    }

}
