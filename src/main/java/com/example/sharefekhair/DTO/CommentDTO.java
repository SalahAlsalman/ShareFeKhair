package com.example.sharefekhair.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor @Data
public class CommentDTO {
    @NotEmpty(message = "message is required")
    private String message;
    @NotNull(message = "note_id is required")
    private UUID note_id;
}
