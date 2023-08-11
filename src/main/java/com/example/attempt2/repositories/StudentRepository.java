package com.example.attempt2.repositories;

import com.example.attempt2.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findStudentById(Long id);
    List<Student> findStudentById(long id);
}
