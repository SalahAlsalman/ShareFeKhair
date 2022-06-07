package com.example.sharefekhair.service;

import com.example.sharefekhair.exceptions.MyClassNotFoundException;
import com.example.sharefekhair.exceptions.TeacherNotFoundException;
import com.example.sharefekhair.model.MyClass;
import com.example.sharefekhair.model.Teacher;
import com.example.sharefekhair.repository.ClassRepository;
import com.example.sharefekhair.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ClassRepository classRepository;

    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    public void addTeacher(Teacher teacher){
        teacherRepository.save(teacher);
    }

    public void addTeacherToClass(Integer teacher_id, Integer class_id) {
        Teacher teacher = teacherRepository.findById(teacher_id).orElseThrow(()->{
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
