package com.example.sharefekhair.controller;

import com.example.sharefekhair.DTO.ResponseAPI;
import com.example.sharefekhair.model.MyClass;
import com.example.sharefekhair.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/class")
public class ClassController {

    private final ClassService classService;
    @GetMapping
    public ResponseEntity<ResponseAPI<?>> getClasses() {
        return ResponseEntity.status(200).body(new ResponseAPI<>(classService.getClasses(),200));
    }

    @GetMapping("/myclasses")
    public ResponseEntity<ResponseAPI<?>> getClassesMyClasses() {
        return ResponseEntity.status(200).body(new ResponseAPI<>(classService.getMyClasses(),200));
    }

    @PostMapping
    public ResponseEntity<ResponseAPI<?>> addClass(@RequestBody @Valid MyClass myClass) {
        classService.addClass(myClass);
        return ResponseEntity.status(201).body(new ResponseAPI<>("Class added",201));
    }
    @PostMapping("/addUserToClass/{class_id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ResponseAPI<?>> addUserToClass(@PathVariable UUID class_id) {
        classService.addUserToClass(class_id);
        return ResponseEntity.status(200).body(new ResponseAPI<>("Class added to user",200));
    }

    @PutMapping
    public ResponseEntity<ResponseAPI<?>> updateClass(@RequestParam UUID class_id, @RequestParam String name) {
        classService.updateClass(class_id, name);
        return ResponseEntity.status(200).body(new ResponseAPI<>("Class updated", 200));
    }

    @DeleteMapping("/{class_id}")
    public ResponseEntity<ResponseAPI<?>> deleteClass(@PathVariable UUID class_id){
        classService.deleteClass(class_id);
        return ResponseEntity.status(200).body(new ResponseAPI<>("Class removed",200));
    }
}
