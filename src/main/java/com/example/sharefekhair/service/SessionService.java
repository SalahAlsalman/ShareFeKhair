package com.example.sharefekhair.service;

import com.example.sharefekhair.DTO.MySessionDTO;
import com.example.sharefekhair.exceptions.MyClassNotFoundException;
import com.example.sharefekhair.exceptions.TeacherNotFoundException;
import com.example.sharefekhair.model.MyClass;
import com.example.sharefekhair.model.MySession;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ClassRepository classRepository;


    public List<MySession> getSessions() {
        return sessionRepository.findAll();
    }

    public void addSession(MySessionDTO mySessionDTO) {
        Teacher teacher = teacherRepository.findById(mySessionDTO.getTeacher_id()).orElseThrow(()->{
            throw new TeacherNotFoundException("teacher_id is wrong");
        });
        MyClass myClass2 = classRepository.findById(mySessionDTO.getClass_id()).orElseThrow(()->{
            throw new MyClassNotFoundException("class_id is wrong");
        });
        MySession mySession = new MySession(null,teacher,myClass2,new HashSet<>(),new HashSet<>());

        List<Student> students= studentRepository.findAll();
        for (int i = 0; i <students.size(); i++) {
            Student student = students.get(i);
            for(MyClass myClass: student.getClasses()){
                if(myClass.getId().equals(mySessionDTO.getClass_id())){
                    mySession.getStudentSet().add(student);
                    studentRepository.save(student);
                }
            }
        }
        sessionRepository.save(mySession);
    }
}
