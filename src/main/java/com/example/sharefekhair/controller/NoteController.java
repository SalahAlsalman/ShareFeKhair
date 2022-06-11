package com.example.sharefekhair.controller;

import com.example.sharefekhair.DTO.NoteDTO;
import com.example.sharefekhair.DTO.ResponseAPI;
import com.example.sharefekhair.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<ResponseAPI<?>> getNotes(){
        return ResponseEntity.status(200).body(new ResponseAPI<>(noteService.getNotes(), 200));
    }

    @PostMapping
    public ResponseEntity<ResponseAPI<?>> addNote(@RequestBody @Valid NoteDTO noteDTO){
        noteService.addNote(noteDTO);
        return ResponseEntity.status(200).body(new ResponseAPI<>("Note Added", 200));
    }

    @DeleteMapping("/{note_id}")
    public ResponseEntity<ResponseAPI<?>> deleteNote(@PathVariable Integer note_id) {
        noteService.deleteNote(note_id);
        return ResponseEntity.status(200).body(new ResponseAPI<>("Note Deleted!",200));
    }
}
