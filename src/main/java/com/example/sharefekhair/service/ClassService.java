package com.example.sharefekhair.service;

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
public class ClassService {

    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final NoteRepository noteRepository;
    private final CommentRepository commentRepository;
    private final SessionRepository sessionRepository;

    public List<MyClass> getClasses() {
        return classRepository.findAll();
    }

    public List<MyClass> getMyClasses() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userRepository.findMyUserByUsername(authentication.getName()).orElseThrow(() -> {
            throw new UsernameNotFoundException("username is wrong");
        });

        if (user.getRole().equals("student")) {
            Student student = studentRepository.findById(user.getId()).orElseThrow(() -> {
                throw new StudentNotFoundException("user_id is wrong");
            });
            return classRepository.findByStudentSetIsContaining(student).get();

        }

        if (user.getRole().equals("teacher")) {
            Teacher teacher = teacherRepository.findById(user.getId()).orElseThrow(() -> {
                throw new TeacherNotFoundException("user_id is wrong");
            });

            return classRepository.findMyClassesByTeacher(teacher).get();

        }
        return new ArrayList<>();
//        return classRepository.findAll();
    }

    public void addClass(MyClass myClass){
        classRepository.save(myClass);
    }

    public void addUserToClass(Integer class_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userRepository.findMyUserByUsername(authentication.getName()).orElseThrow(()->{
            throw new UsernameNotFoundException("username is wrong");
        });
        if (user.getRole().equals("teacher")) {
            Teacher teacher = teacherRepository.findTeacherByUser(user).orElseThrow(()->{
                throw new TeacherNotFoundException("teacher_id is wrong!");
            });
            MyClass myClass= classRepository.findById(class_id).orElseThrow(()->{
                throw new MyClassNotFoundException("class_id is wrong");
            });

            if (teacher.getClasses().contains(myClass)) {
                throw new ClassAlreadyRegisteredException("Class is already registered");
            }

            teacher.getClasses().add(myClass);
            myClass.setTeacher(teacher);
            teacherRepository.save(teacher);
            classRepository.save(myClass);
        }
        if (user.getRole().equals("student")) {
            Student student = studentRepository.findStudentByUser(user).orElseThrow(()->{
                throw new UsernameNotFoundException("you're not student");
            });
            MyClass myClass= classRepository.findById(class_id).orElseThrow(()->{
                throw new MyClassNotFoundException("class_id is wrong");
            });

            if (student.getClasses().contains(myClass)) {
                throw new ClassAlreadyRegisteredException("Class is already registered");
            }

            student.getClasses().add(myClass);
            myClass.getStudentSet().add(student);
            studentRepository.save(student);
            classRepository.save(myClass);
        }
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
