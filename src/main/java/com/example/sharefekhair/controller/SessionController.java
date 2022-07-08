package com.example.sharefekhair.controller;

import com.example.sharefekhair.DTO.MySessionDTO;
import com.example.sharefekhair.DTO.ResponseAPI;
import com.example.sharefekhair.model.MySession;
import com.example.sharefekhair.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping
    public ResponseEntity<ResponseAPI<?>> getSessions(){
        return ResponseEntity.status(200).body(new ResponseAPI<>(sessionService.getSessions(), 200));
    }

    @GetMapping("/{class_id}")
    public ResponseEntity<ResponseAPI<?>> getSessionsByClass(@PathVariable UUID class_id){
        return ResponseEntity.status(200).body(new ResponseAPI<>(sessionService.getSessionsByClass(class_id), 200));
    }

    @PostMapping
    public ResponseEntity<ResponseAPI<?>> addSession(@RequestBody @Valid MySessionDTO MySessionDTO){
        sessionService.addSession(MySessionDTO);
        return ResponseEntity.status(201).body(new ResponseAPI<>("Session created",201));
    }

    @DeleteMapping("/{session_id}")
    public ResponseEntity<ResponseAPI<?>> deleteSession(@PathVariable UUID session_id){
        sessionService.deleteSession(session_id);
        return ResponseEntity.status(200).body(new ResponseAPI<>("Session Deleted",200));
    }


}
