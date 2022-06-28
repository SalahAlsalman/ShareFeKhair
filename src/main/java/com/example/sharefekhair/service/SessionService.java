package com.example.sharefekhair.service;

import com.example.sharefekhair.DTO.MySessionDTO;
import com.example.sharefekhair.exceptions.ClassIdIsNotFoundException;
import com.example.sharefekhair.exceptions.MyClassNotFoundException;
import com.example.sharefekhair.exceptions.NoRightsException;
import com.example.sharefekhair.exceptions.SessionIdNotFoundException;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;
    private final CommentRepository commentRepository;
    private final ClassRepository classRepository;


    public List<MySession> getSessions() {
        return sessionRepository.findAll();
    }

    public List<MySession> getSessionsByClass(Integer class_id) {
        MyClass myClass = classRepository.findById(class_id).orElseThrow(()->{
            throw new ClassIdIsNotFoundException("class_id is wrong");
        });
        List<MySession> sessions=sessionRepository.findMySessionsByMyClass_Id(myClass.getId()).get();
        return sessions;
    }

    public void addSession(MySessionDTO mySessionDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userRepository.findMyUserByUsername(authentication.getName()).orElseThrow(()->{
            throw new UsernameNotFoundException("username is wrong");
        });
        if (user.getRole().equals("teacher")) {
            MyClass myClass2 = classRepository.findById(mySessionDTO.getClass_id()).orElseThrow(()->{
                throw new MyClassNotFoundException("class_id is wrong");
            });
            MySession mySession = new MySession(null,myClass2,new ArrayList<>());
            sessionRepository.save(mySession);
            return;
        }
        throw new NoRightsException("You dont have authority to make session");
    }

    public void deleteSession(Integer session_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userRepository.findMyUserByUsername(authentication.getName()).orElseThrow(()->{
            throw new UsernameNotFoundException("username is wrong");
        });
        MySession session = sessionRepository.findById(session_id).orElseThrow(()->{
            throw new SessionIdNotFoundException("session_id is wrong");
        });

        if (user.getId().equals(session.getMyClass().getTeacher().getId())){
            List<Note> notes = noteRepository.findNotesByMySession_Id(session_id).get();
            if (notes.size() > 0) {
                List<Comment> comments = commentRepository.findCommentsByNote_Id(notes.get(0).getId()).get();
                if (comments.size() >0) {
                    commentRepository.deleteAll(comments);
                }
                noteRepository.deleteAll(notes);
            }
            sessionRepository.delete(session);
            return;
        }
        throw new NoRightsException("You don't have right to delete this session");
    }
}
