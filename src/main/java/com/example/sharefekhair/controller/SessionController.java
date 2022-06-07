package com.example.sharefekhair.controller;

import com.example.sharefekhair.DTO.MySessionDTO;
import com.example.sharefekhair.DTO.ResponseAPI;
import com.example.sharefekhair.model.MySession;
import com.example.sharefekhair.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping
    public ResponseEntity<ResponseAPI<?>> getSessions(){
        return ResponseEntity.status(200).body(new ResponseAPI<>(sessionService.getSessions(), 200));
    }

    @PostMapping
    public ResponseEntity<ResponseAPI<?>> addSession(@RequestBody @Valid MySessionDTO MySessionDTO){
        sessionService.addSession(MySessionDTO);
        return ResponseEntity.status(201).body(new ResponseAPI<>("Session created",200));
    }


}
