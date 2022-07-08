package com.example.sharefekhair.service;

import com.example.sharefekhair.exceptions.ClassAlreadyRegisteredException;
import com.example.sharefekhair.exceptions.MyClassNotFoundException;
import com.example.sharefekhair.exceptions.StudentNotFoundException;
import com.example.sharefekhair.model.MyClass;
import com.example.sharefekhair.model.MySession;
import com.example.sharefekhair.model.MyUser;
import com.example.sharefekhair.model.Student;
import com.example.sharefekhair.repository.ClassRepository;
import com.example.sharefekhair.repository.StudentRepository;
import com.example.sharefekhair.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student){
        studentRepository.save(student);
    }

    public void addStudentToClass(UUID class_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userRepository.findMyUserByUsername(authentication.getName()).orElseThrow(()->{
            throw new UsernameNotFoundException("username is wrong");
        });
        Student student = studentRepository.findById(user.getId()).orElseThrow(()->{
            throw new StudentNotFoundException("you're not student");
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
