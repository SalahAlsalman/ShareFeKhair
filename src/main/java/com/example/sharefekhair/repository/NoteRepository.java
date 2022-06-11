package com.example.sharefekhair.repository;

import com.example.sharefekhair.model.MyUser;
import com.example.sharefekhair.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    Optional<List<Note>> findNotesByMySession_Id(Integer session_id);
    Optional<List<Note>> findNotesByUser(MyUser user);
}
