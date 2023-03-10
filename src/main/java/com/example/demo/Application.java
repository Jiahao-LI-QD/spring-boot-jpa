package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student maria = new Student(
                    "Maria",
                    "Jones",
                    "maria.jones@amigoscode.com",
                    21
            );
            Student maria2 = new Student(
                    "Maria",
                    "Jones",
                    "maria.jones1@amigoscode.com",
                    23
            );
            Student ahmed = new Student(
                    "Ahmed",
                    "Ali",
                    "ahmed.ali@amigoscode.edu",
                    21
            );

            studentRepository.saveAll(List.of(maria, maria2, ahmed));

            studentRepository
                    .findStudentByEmail("admed.ali@amigoscode.edu")
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("does not exist")
                    );

            studentRepository
                    .findStudentsByFirstNameEqualsAndAgeIsGreaterThan(
                            "Maria", 21)
                    .forEach(System.out::println);

            studentRepository
                    .selectStudentByFirstNameAndAge(
                            "Maria", 21
                    ).forEach(System.out::println);

            System.out.println(studentRepository
                    .deleteStudentById(3L));
        };
    }

}
