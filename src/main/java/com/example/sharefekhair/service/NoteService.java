package com.example.sharefekhair.service;

import com.example.sharefekhair.DTO.NoteDTO;
import com.example.sharefekhair.DTO.UpdateNoteDTO;
import com.example.sharefekhair.exceptions.*;
import com.example.sharefekhair.model.*;
import com.example.sharefekhair.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SessionRepository sessionRepository;

    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    public void addNote(NoteDTO noteDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userRepository.findMyUserByUsername(authentication.getName()).orElseThrow(()->{
            throw new UsernameNotFoundException("username is wrong");
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

    public void deleteNote(Integer note_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userRepository.findMyUserByUsername(authentication.getName()).orElseThrow(()->{
            throw new UsernameNotFoundException("username is wrong");
        });
        Note note = noteRepository.findById(note_id).orElseThrow(()->{
            throw new NoteIdNotFoundException("note_id is wrong");
        });
        if (note.getUser().getId() != user.getId()){
            throw new YoureNotOwnerOfThisNoteException("you don't own this note");
        }
        MySession session = sessionRepository.findById(note.getMySession().getId()).orElseThrow(()->{
            throw new SessionIdNotFoundException("session_id is wrong");
        });
        if (note.getComments().size()>0){
            commentRepository.deleteAll(note.getComments());
        }
        noteRepository.delete(note);
        user.getNotes().remove(note);
        session.getNotes().remove(note);
    }

    public void updateNote(Integer note_id, UpdateNoteDTO noteDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userRepository.findMyUserByUsername(authentication.getName()).orElseThrow(()->{
            throw new UsernameNotFoundException("username is wrong");
        });
        Note note = noteRepository.findById(note_id).orElseThrow(()->{
            throw new NoteIdNotFoundException("note_id is wrong");
        });
        if (note.getUser().getId() != user.getId()){
            throw new YoureNotOwnerOfThisNoteException("you don't own this note");
        }
        note.setMessage(noteDTO.getMessage());
        noteRepository.save(note);
    }
}
