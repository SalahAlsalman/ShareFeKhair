package com.example.sharefekhair.service;

import com.example.sharefekhair.DTO.CommentDTO;
import com.example.sharefekhair.DTO.NoteDTO;
import com.example.sharefekhair.exceptions.NoteIdNotFoundException;
import com.example.sharefekhair.exceptions.SessionIdNotFoundException;
import com.example.sharefekhair.exceptions.UserIdNotFoundException;
import com.example.sharefekhair.model.Comment;
import com.example.sharefekhair.model.MySession;
import com.example.sharefekhair.model.MyUser;
import com.example.sharefekhair.model.Note;
import com.example.sharefekhair.repository.CommentRepository;
import com.example.sharefekhair.repository.NoteRepository;
import com.example.sharefekhair.repository.SessionRepository;
import com.example.sharefekhair.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public void addComment(CommentDTO commentDTO) {
        MyUser user= userRepository.findById(commentDTO.getUser_id()).orElseThrow(()->{
            throw new UserIdNotFoundException("user_id is wrong");
        });

        Note note = noteRepository.findById(commentDTO.getNote_id()).orElseThrow(()->{
            throw new NoteIdNotFoundException("note_id is wrong");
        });

        Comment comment = new Comment(null,commentDTO.getMessage(),null,user,note);
        commentRepository.save(comment);
        note.getComments().add(comment);

    }
}
