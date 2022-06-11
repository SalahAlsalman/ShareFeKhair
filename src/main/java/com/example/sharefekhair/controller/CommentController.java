package com.example.sharefekhair.controller;

import com.example.sharefekhair.DTO.CommentDTO;
import com.example.sharefekhair.DTO.NoteDTO;
import com.example.sharefekhair.DTO.ResponseAPI;
import com.example.sharefekhair.DTO.UpdateCommentDTO;
import com.example.sharefekhair.service.CommentService;
import com.example.sharefekhair.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<ResponseAPI<?>> getComments(){
        return ResponseEntity.status(200).body(new ResponseAPI<>(commentService.getComments(), 200));
    }

    @PostMapping
    public ResponseEntity<ResponseAPI<?>> addComment(@RequestBody @Valid CommentDTO commentDTO){
        commentService.addComment(commentDTO);
        return ResponseEntity.status(200).body(new ResponseAPI<>("Comment Added", 200));
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<ResponseAPI<?>> updateComment(@PathVariable Integer comment_id,@RequestBody @Valid UpdateCommentDTO commentDTO){
        commentService.updateComment(comment_id, commentDTO);
        return ResponseEntity.status(200).body(new ResponseAPI<>("Comment updated", 200));
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<ResponseAPI<?>> deleteComment(@PathVariable Integer comment_id) {
        commentService.deleteComment(comment_id);
        return ResponseEntity.status(200).body(new ResponseAPI<>("Comment Deleted!",200));
    }
}
