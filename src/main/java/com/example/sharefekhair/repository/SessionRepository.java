package com.example.sharefekhair.repository;

import com.example.sharefekhair.model.MySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface SessionRepository extends JpaRepository<MySession, Integer> {
}
