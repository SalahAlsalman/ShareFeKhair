package com.example.sharefekhair.service;

import com.example.sharefekhair.model.MyClass;
import com.example.sharefekhair.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassRepository classRepository;

    public List<MyClass> getClasses() {
        return classRepository.findAll();
    }

    public void addClass(MyClass myClass){
        classRepository.save(myClass);
    }
}
