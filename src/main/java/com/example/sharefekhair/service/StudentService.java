package com.example.sharefekhair.service;

import com.example.sharefekhair.exceptions.MyClassNotFoundException;
import com.example.sharefekhair.exceptions.StudentNotFoundException;
import com.example.sharefekhair.model.MyClass;
import com.example.sharefekhair.model.MySession;
import com.example.sharefekhair.model.Student;
import com.example.sharefekhair.repository.ClassRepository;
import com.example.sharefekhair.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student){
        studentRepository.save(student);
    }

    public void addStudentToClass(Integer student_id, Integer class_id) {
        Student student = studentRepository.findById(student_id).orElseThrow(()->{
            throw new StudentNotFoundException("student_id is wrong!");
        });
        MyClass myClass= classRepository.findById(class_id).orElseThrow(()->{
            throw new MyClassNotFoundException("class_id is wrong");
        });
        if (myClass.getSessions().size() >0){
            List<MySession> sessions = new ArrayList<>(myClass.getSessions());
            Set<MySession> sessionSet = new HashSet<>(sessions);
            myClass.setSessions(sessionSet);
        }
        student.getClasses().add(myClass);
        myClass.getStudentSet().add(student);
        studentRepository.save(student);
        classRepository.save(myClass);
    }
}
