package com.example.sharefekhair.repository;

import com.example.sharefekhair.model.MyClass;
import com.example.sharefekhair.model.MySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<MySession, Integer> {
    Optional<List<MySession>> findMySessionsByMyClass_Id(Integer class_id);
    Optional<List<MySession>> findMySessionsByMyClass(MyClass myClass);
}
