package com.example.sharefekhair.service;

import com.example.sharefekhair.DTO.NoteDTO;
import com.example.sharefekhair.exceptions.*;
import com.example.sharefekhair.model.*;
import com.example.sharefekhair.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
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

        if (user.getRole().equals("student")){
            Integer classIdOfNote = session.getMyClass().getId();
            Student student= studentRepository.findById(user.getId()).orElseThrow(()->{
                throw new StudentNotFoundException("student_id is wrong!");
            });
            List<MyClass> userClasses = new ArrayList<>(student.getClasses());
            for (int i = 0; i < userClasses.size(); i++) {
                MyClass myClass= userClasses.get(i);
                if (myClass.getId().equals(classIdOfNote)){
                    Note note = new Note(null,noteDTO.getMessage(),null,user,session);
                    noteRepository.save(note);
                    user.getNotes().add(note);
                    session.getNotes().add(note);
                    return;
                }
            }
            throw new UserIdDoesntHaveThisClassException("this user is not in this class");
        }
        if (user.getRole().equals("teacher")){
            Integer classIdOfNote = session.getMyClass().getId();
            Teacher teacher= teacherRepository.findById(user.getId()).orElseThrow(()->{
                throw new TeacherNotFoundException("teacher_id is wrong!");
            });
            List<MyClass> userClasses = new ArrayList<>(teacher.getClasses());
            for (int i = 0; i < userClasses.size(); i++) {
                MyClass myClass= userClasses.get(i);
                if (myClass.getId().equals(classIdOfNote)){
                    Note note = new Note(null,noteDTO.getMessage(),null,user,session);
                    noteRepository.save(note);
                    user.getNotes().add(note);
                    session.getNotes().add(note);
                    return;
                }
            }
            throw new UserIdDoesntHaveThisClassException("this user is not in this class");
        }
    }
}
