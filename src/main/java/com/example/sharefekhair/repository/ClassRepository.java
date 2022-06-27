package com.example.sharefekhair.repository;

import com.example.sharefekhair.model.MyClass;
import com.example.sharefekhair.model.Student;
import com.example.sharefekhair.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<MyClass, Integer> {
    Optional<List<MyClass>> findMyClassesByTeacher(Teacher teacher);
    @Query(value = "SELECT * FROM student_class where student_id = :student_id", nativeQuery = true)
    Optional<List<MyClass>> findMyClassesByStudent(@Param("student_id") Integer student_id);

}
