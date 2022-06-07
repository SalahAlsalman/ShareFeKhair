package com.example.sharefekhair.controller;

import com.example.sharefekhair.DTO.ResponseAPI;
import com.example.sharefekhair.model.MyUser;
import com.example.sharefekhair.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ResponseAPI<?>> getUsers() {
        return ResponseEntity.status(200).body(new ResponseAPI<>(userService.getUsers(),200));
    }

    @PostMapping
    public ResponseEntity<ResponseAPI<?>> addUser(@RequestBody @Valid MyUser user) {
        userService.addUser(user);
        return ResponseEntity.status(201).body(new ResponseAPI<>("User added", 201));
    }
}
