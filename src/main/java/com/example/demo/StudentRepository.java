package com.example.demo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    @Query("SELECT s From Student s WHERE s.firstName = :firstName AND s.age >= :age")
    List<Student> findStudentsByFirstNameEqualsAndAgeIsGreaterThan(@Param("firstName") String firstName,
                                                                   @Param("age") Integer age);

    @Query(value = "SELECT * From student WHERE first_name = :firstName AND age >= :age", nativeQuery = true)
    List<Student> selectStudentByFirstNameAndAge(
            @Param("firstName") String firstName,
            @Param("age") Integer age);

    @Transactional
    @Modifying
    @Query("DELETE FROM Student u WHERE u.id = :id")
    Integer deleteStudentById(Long id);
}
