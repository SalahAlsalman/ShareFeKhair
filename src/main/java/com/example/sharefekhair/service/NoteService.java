package com.example.sharefekhair.service;

import com.example.sharefekhair.DTO.NoteDTO;
import com.example.sharefekhair.exceptions.SessionIdNotFoundException;
import com.example.sharefekhair.exceptions.UserIdNotFoundException;
import com.example.sharefekhair.model.MySession;
import com.example.sharefekhair.model.MyUser;
import com.example.sharefekhair.model.Note;
import com.example.sharefekhair.repository.NoteRepository;
import com.example.sharefekhair.repository.SessionRepository;
import com.example.sharefekhair.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    public void addNote(NoteDTO noteDTO) {
        MyUser user= userRepository.findById(noteDTO.getUser_id()).orElseThrow(()->{
            throw new UserIdNotFoundException("user_id is wrong");
        });

        MySession session = sessionRepository.findById(noteDTO.getSession_id()).orElseThrow(()->{
            throw new SessionIdNotFoundException("session_id is wrong");
        });

        Note note = new Note(null,noteDTO.getMessage(),null,user,session);
        noteRepository.save(note);
        user.getNotes().add(note);
        session.getNotes().add(note);
    }
}
