package com.example.sharefekhair.repository;

import com.example.sharefekhair.model.Comment;
import com.example.sharefekhair.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
