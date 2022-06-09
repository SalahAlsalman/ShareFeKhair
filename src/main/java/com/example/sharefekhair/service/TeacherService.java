package com.example.sharefekhair.service;

import com.example.sharefekhair.exceptions.MyClassNotFoundException;
import com.example.sharefekhair.exceptions.TeacherNotFoundException;
import com.example.sharefekhair.model.MyClass;
import com.example.sharefekhair.model.MyUser;
import com.example.sharefekhair.model.Teacher;
import com.example.sharefekhair.repository.ClassRepository;
import com.example.sharefekhair.repository.TeacherRepository;
import com.example.sharefekhair.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    public void addTeacher(Teacher teacher){
        teacherRepository.save(teacher);
    }

    public void addTeacherToClass(Integer class_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userRepository.findMyUserByUsername(authentication.getName()).orElseThrow(()->{
            throw new UsernameNotFoundException("username is wrong");
        });
        Teacher teacher = teacherRepository.findTeacherByUser(user).orElseThrow(()->{
            throw new TeacherNotFoundException("teacher_id is wrong!");
        });
        MyClass myClass= classRepository.findById(class_id).orElseThrow(()->{
            throw new MyClassNotFoundException("class_id is wrong");
        });

        teacher.getClasses().add(myClass);
        myClass.setTeacher(teacher);
        teacherRepository.save(teacher);
        classRepository.save(myClass);
    }
}
