package com.example.sharefekhair.service;

import com.example.sharefekhair.exceptions.ClassIdIsNotFoundException;
import com.example.sharefekhair.exceptions.TeacherNotFoundException;
import com.example.sharefekhair.model.*;
import com.example.sharefekhair.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassRepository classRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final NoteRepository noteRepository;
    private final CommentRepository commentRepository;
    private final SessionRepository sessionRepository;

    public List<MyClass> getClasses() {
        return classRepository.findAll();
    }

    public void addClass(MyClass myClass){
        classRepository.save(myClass);
    }

    public void deleteClass(Integer class_id) {

        MyClass myClass = classRepository.findById(class_id).orElseThrow(()->{
            throw new ClassIdIsNotFoundException("class_id is wrong");
        });
        Teacher teacher = teacherRepository.findById(myClass.getTeacher().getId()).orElseThrow(()->{
            throw new TeacherNotFoundException("teacher_id is wrong");
        });
        myClass.setTeacher(null);
        List<MySession> sessions=sessionRepository.findMySessionsByMyClass_Id(myClass.getId()).get();
        if (sessions.size() > 0) {
            List<Note> notes = noteRepository.findNotesByMySession_Id(sessions.get(0).getId()).get();
            if (notes.size() > 0) {
                List<Comment> comments = commentRepository.findCommentsByNote_Id(notes.get(0).getId()).get();
                if (comments.size() > 0) {
                    commentRepository.deleteAll(comments);
                }
                noteRepository.deleteAll(notes);
            }
            sessionRepository.deleteAll(sessions);
        }
        classRepository.delete(myClass);
    }

    public void updateClass(Integer class_id, String name) {
        MyClass myClass = classRepository.findById(class_id).orElseThrow(()->{
            throw new ClassIdIsNotFoundException("class_id is wrong");
        });
        myClass.setName(name);
        classRepository.save(myClass);
    }
}
