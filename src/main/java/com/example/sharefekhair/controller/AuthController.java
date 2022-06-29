package com.example.sharefekhair.controller;

import com.example.sharefekhair.DTO.ResponseAPI;
import com.example.sharefekhair.model.MyUser;
import com.example.sharefekhair.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseAPI<?>> register(@RequestBody @Valid MyUser user) {
        userService.addUser(user);
        return ResponseEntity.status(201).body(new ResponseAPI<>("User added", 201));
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseAPI<?>> login(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.status(200).body(new ResponseAPI<>(authentication.getAuthorities(),200));
    }



}
