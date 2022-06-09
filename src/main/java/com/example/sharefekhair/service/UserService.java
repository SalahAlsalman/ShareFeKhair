package com.example.sharefekhair.service;

import com.example.sharefekhair.model.MyUser;
import com.example.sharefekhair.model.Student;
import com.example.sharefekhair.model.Teacher;
import com.example.sharefekhair.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
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
            studentService.addStudent(new Student(null, user, new HashSet<>(), new HashSet<>()));
        if (user.getRole().equals("teacher"))
            teacherService.addTeacher(new Teacher(null, user, new HashSet<>(), new HashSet<>()));
    }
}

