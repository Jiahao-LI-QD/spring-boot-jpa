package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository studentRepository) {
        return args -> {
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            Integer age = faker.number().numberBetween(17, 55);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    age
            );
            StudentIdCard studentIdCard = new StudentIdCard("123456789", student);
            student.addBook(new Book("Clean Code", LocalDateTime.now().minusDays(4)));
            student.addBook(new Book("Spring Boot", LocalDateTime.now().minusDays(23)));
            student.addBook(new Book("Spring JPA", LocalDateTime.now().minusYears(2)));

            student.setStudentIdCard(studentIdCard);

//            student.enrolToCourse(new Course("Computer Science", "IT"));
//
//            student.enrolToCourse(new Course("Amigoscode Spring Data JPA", "IT"));

            student.addEnrolment(new Enrolment(student, new Course("Computer Science", "IT"),
                    LocalDateTime.now()));
            student.addEnrolment(new Enrolment(student, new Course("Amigoscode Spring Data JPA", "IT"),
                    LocalDateTime.now().minusYears(1)));

            studentRepository.save(student);

            studentRepository.findById(1L).ifPresent(s -> {
                System.out.println("fetch book lazy...");
                List<Book> books = student.getBooks();
                books.forEach(book -> {
                    System.out.println(
                            s.getFirstName() + "borrowed " + book.getBookName()
                    );
                }
                );
            });
//
//            studentIdCardRepository.findById(1L)
//                    .ifPresent(System.out::println);
//            studentIdCardRepository.deleteById(1L);

        };
    }


    private static void generateRandomStudent(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i ++){
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            Integer age = faker.number().numberBetween(17, 55);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    age
            );
            studentRepository.save(student);
        }
    }

}
