package com.example.sharefekhair.repository;

import com.example.sharefekhair.model.MyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<MyClass, Integer> {
}
