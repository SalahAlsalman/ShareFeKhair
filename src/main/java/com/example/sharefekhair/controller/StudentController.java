package com.example.sharefekhair.controller;

import com.example.sharefekhair.DTO.ResponseAPI;
import com.example.sharefekhair.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<ResponseAPI<?>> getStudents() {
        return ResponseEntity.status(200).body(new ResponseAPI<>(studentService.getStudents(),200));
    }

    @PostMapping
    public ResponseEntity<ResponseAPI<?>> addStudentToClass(@RequestParam Integer class_id){
        studentService.addStudentToClass(class_id);
        return ResponseEntity.status(201).body(new ResponseAPI<>("Class Added", 201));
    }
}
