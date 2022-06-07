package com.example.sharefekhair.repository;

import com.example.sharefekhair.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}
