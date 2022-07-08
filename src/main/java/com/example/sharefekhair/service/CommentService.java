package com.example.sharefekhair.service;

import com.example.sharefekhair.DTO.CommentDTO;
import com.example.sharefekhair.DTO.NoteDTO;
import com.example.sharefekhair.DTO.UpdateCommentDTO;
import com.example.sharefekhair.exceptions.*;
import com.example.sharefekhair.model.*;
import com.example.sharefekhair.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final NoteRepository noteRepository;

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public void addComment(CommentDTO commentDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userRepository.findMyUserByUsername(authentication.getName()).orElseThrow(() -> {
            throw new UsernameNotFoundException("username is wrong");
        });

        Note note = noteRepository.findById(commentDTO.getNote_id()).orElseThrow(() -> {
            throw new NoteIdNotFoundException("note_id is wrong");
        });
        if (user.getRole().equals("student")) {
            UUID classIdOfNote = note.getMySession().getMyClass().getId();
            Student student = studentRepository.findById(user.getId()).orElseThrow(() -> {
                throw new StudentNotFoundException("student_id is wrong!");
            });
            List<MyClass> userClasses = new ArrayList<>(student.getClasses());
            for (int i = 0; i < userClasses.size(); i++) {
                MyClass myClass = userClasses.get(i);
                if (myClass.getId().equals(classIdOfNote)) {
                    Comment comment = new Comment(null, commentDTO.getMessage(), null, user, note);
                    commentRepository.save(comment);
                    note.getComments().add(comment);
                    return;
                }
            }
            throw new UserIdDoesntHaveThisClassException("this user is not in this class");
        }
        if (user.getRole().equals("teacher")) {
            UUID classIdOfNote = note.getMySession().getMyClass().getId();
            Teacher teacher = teacherRepository.findById(user.getId()).orElseThrow(() -> {
                throw new StudentNotFoundException("teacher_id is wrong!");
            });
            List<MyClass> userClasses = teacher.getClasses();
            for (int i = 0; i < userClasses.size(); i++) {
                MyClass myClass = userClasses.get(i);
                if (myClass.getId().equals(classIdOfNote)) {
                    Comment comment = new Comment(null, commentDTO.getMessage(), null, user, note);
                    commentRepository.save(comment);
                    note.getComments().add(comment);
                    return;
                }
            }
            throw new UserIdDoesntHaveThisClassException("this user is not in this class");
        }


    }

    public void deleteComment(UUID comment_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userRepository.findMyUserByUsername(authentication.getName()).orElseThrow(() -> {
            throw new UsernameNotFoundException("username is wrong");
        });

        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> {
            throw new CommentIdNotFoundException("comment_id is wrong");
        });
        if (comment.getUser().getId() != user.getId()) {
            throw new YoureNotOwnerOfThisNoteException("you don't own this comment");
        }

        Note note = noteRepository.findById(comment.getNote().getId()).orElseThrow(() -> {
            throw new SessionIdNotFoundException("note_id is wrong");
        });
        note.getComments().remove(comment);
        commentRepository.delete(comment);
    }

    public void updateComment(UUID comment_id, UpdateCommentDTO commentDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userRepository.findMyUserByUsername(authentication.getName()).orElseThrow(() -> {
            throw new UsernameNotFoundException("username is wrong");
        });

        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> {
            throw new CommentIdNotFoundException("comment_id is wrong");
        });
        if (user.getId() != comment.getUser().getId()) {
            throw new YoureNotOwnerOfThisCommentException("you don't own this comment");
        }
        comment.setMessage(commentDTO.getMessage());
        commentRepository.save(comment);
    }
}
