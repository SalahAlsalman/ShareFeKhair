package com.example.sharefekhair.service;

import com.example.sharefekhair.exceptions.TeacherNotFoundException;
import com.example.sharefekhair.exceptions.UserIdNotFoundException;
import com.example.sharefekhair.exceptions.YoureNotOwnerOfThisUserException;
import com.example.sharefekhair.model.*;
import com.example.sharefekhair.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final SessionRepository sessionRepository;
    private final StudentService studentService;
    private final TeacherService teacherService;

    public List<MyUser> getUsers() {
        return userRepository.findAll();
    }


    public void addUser(MyUser user) {
        String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        if (user.getRole().equals("student"))
            studentService.addStudent(new Student(null, user, new HashSet<>()));
        if (user.getRole().equals("teacher"))
            teacherService.addTeacher(new Teacher(null, user, new HashSet<>()));
    }

    //TODO: notWorking
    public void deleteUser(Integer user_id) {
        MyUser user = userRepository.findById(user_id).orElseThrow(()->{
            throw new UserIdNotFoundException("user_id is wrong");
        });
        if (user.getRole().equals("teacher")){
            Teacher teacher = teacherRepository.findById(user.getId()).orElseThrow(()->{
                throw new TeacherNotFoundException("user_id is wrong");
            });
            List<MyClass> classes = new ArrayList<>(teacher.getClasses());
            for (int i = 0; i < classes.size(); i++) {
                MyClass myClass = classes.get(i);
                myClass.setTeacher(null);
                List<MySession> sessions = new ArrayList<>(myClass.getSessions());
                for (int j = 0; j < sessions.size(); j++) {
                    MySession session = sessions.get(i);
                    session.setMyClass(myClass);
                }
                myClass.setSessions(new HashSet<>(sessions));
                classRepository.save(myClass);
                teacherRepository.save(teacher);
                //delete teacher_id from session too
            }
            userRepository.delete(user);
            teacherRepository.delete(teacher);
        }
        if (user.getRole().equals("student")){
            Student student = studentRepository.findById(user.getId()).orElseThrow(()->{
                throw new TeacherNotFoundException("student_id is wrong");
            });
            userRepository.delete(user);
            studentRepository.delete(student);
        }
    }
}

