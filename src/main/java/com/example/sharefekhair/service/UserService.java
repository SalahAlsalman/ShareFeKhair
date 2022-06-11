package com.example.sharefekhair.service;

import com.example.sharefekhair.DTO.UpdateUserDTO;
import com.example.sharefekhair.exceptions.*;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final SessionRepository sessionRepository;
    private final NoteRepository noteRepository;
    private final CommentRepository commentRepository;
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
            studentService.addStudent(new Student(null, user, new ArrayList<>()));
        if (user.getRole().equals("teacher"))
            teacherService.addTeacher(new Teacher(null, user, new ArrayList<>()));
    }

    public void updateUser(Integer user_id,UpdateUserDTO newUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userRepository.findMyUserByUsername(authentication.getName()).orElseThrow(() -> {
            throw new UsernameNotFoundException("username is wrong");
        });
        //check if user owns this account
        if (user.getId() != user_id) {
            throw new NoRightsException("you don't own this account");
        }

        user.setUsername(newUser.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());
        user.setPassword(hashPassword);
        user.setEmail(newUser.getEmail());
        user.setRole(newUser.getRole());
        userRepository.save(user);
    }

    public void deleteUser(Integer user_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userRepository.findMyUserByUsername(authentication.getName()).orElseThrow(() -> {
            throw new UsernameNotFoundException("username is wrong");
        });
        //check if user owns this account
        if (user.getId() != user_id) {
            throw new NoRightsException("you don't own this account");
        }

        //check if he's teacher
        if (user.getRole().equals("teacher")) {
            Teacher teacher = teacherRepository.findById(user.getId()).orElseThrow(() -> {
                throw new TeacherNotFoundException("user_id is wrong");
            });
            List<MyClass> classes = classRepository.findMyClassesByTeacher(teacher).get();
            classRepository.deleteAll(classes);
            teacherRepository.delete(teacher);
            userRepository.delete(user);
            return;
        }
        //check if he's student
        if (user.getRole().equals("student")) {
            Student student = studentRepository.findById(user_id).orElseThrow(() -> {
                throw new StudentNotFoundException("user_id is wrong");
            });
            List<MyClass> classes = student.getClasses();
            for (int i = 0; i < classes.size(); i++) {
                classes.get(i).getStudentSet().remove(student);
                classRepository.save(classes.get(i));
                student.getClasses().remove(classes.get(i));
            }
            studentRepository.delete(student);
            userRepository.delete(user);
            return;
        }
        throw new UserIdNotFoundException("you're not student or teacher !");
    }


}


