package com.example.sharefekhair.service;

import com.example.sharefekhair.exceptions.ClassIdIsNotFoundException;
import com.example.sharefekhair.exceptions.TeacherNotFoundException;
import com.example.sharefekhair.model.MyClass;
import com.example.sharefekhair.model.Student;
import com.example.sharefekhair.model.Teacher;
import com.example.sharefekhair.repository.ClassRepository;
import com.example.sharefekhair.repository.SessionRepository;
import com.example.sharefekhair.repository.StudentRepository;
import com.example.sharefekhair.repository.TeacherRepository;
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
        myClass.setTeacher(new Teacher());
        myClass.setStudentSet(new HashSet<>());
        myClass.setSessions(new HashSet<>());
        classRepository.delete(myClass);
    }
}
