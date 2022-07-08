package com.example.sharefekhair.repository;

import com.example.sharefekhair.model.MyUser;
import com.example.sharefekhair.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findStudentByUser(MyUser user);
}
