package com.example.sharefekhair.controller;

import com.example.sharefekhair.DTO.ResponseAPI;
import com.example.sharefekhair.DTO.UpdateUserDTO;
import com.example.sharefekhair.model.MyUser;
import com.example.sharefekhair.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ResponseAPI<?>> getUsers() {
        return ResponseEntity.status(200).body(new ResponseAPI<>(userService.getUsers(),200));
    }

    //Post is register in auth controller

    @PutMapping("/{user_id}")
    public ResponseEntity<ResponseAPI<?>> updateUser(@PathVariable UUID user_id, @RequestBody UpdateUserDTO user) {
        userService.updateUser(user_id,user);
        return ResponseEntity.status(200).body(new ResponseAPI<>("user updated",200));
    }


    @DeleteMapping("/{user_id}")
    public ResponseEntity<ResponseAPI<?>> deleteUser(@PathVariable UUID user_id){
        userService.deleteUser(user_id);
        return ResponseEntity.status(200).body(new ResponseAPI<>("user deleted", 200));
    }


}
