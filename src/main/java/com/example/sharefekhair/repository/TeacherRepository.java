package com.example.sharefekhair.repository;

import com.example.sharefekhair.model.MyUser;
import com.example.sharefekhair.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    Optional<Teacher> findTeacherByUser(MyUser user);
}
