package com.example.sharefekhair.controller;

import com.example.sharefekhair.DTO.ResponseAPI;
import com.example.sharefekhair.model.MyUser;
import com.example.sharefekhair.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseAPI<?>> register(@RequestBody @Valid MyUser user) {
        userService.addUser(user);
        return ResponseEntity.status(201).body(new ResponseAPI<>("User added", 201));
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseAPI<?>> login(){
        return ResponseEntity.status(200).body(new ResponseAPI<>("Logged",200));
    }



}
