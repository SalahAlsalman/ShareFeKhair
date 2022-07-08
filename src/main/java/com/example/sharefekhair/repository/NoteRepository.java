package com.example.sharefekhair.repository;

import com.example.sharefekhair.model.MyUser;
import com.example.sharefekhair.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    Optional<List<Note>> findNotesByMySession_Id(UUID session_id);
    Optional<List<Note>> findNotesByUser(MyUser user);
}
