package com.example.sharefekhair.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@AllArgsConstructor @Data
public class NoteDTO {
    @NotEmpty(message = "message is required")
    private String message;
    @NotNull(message = "session_id is required")
    private Integer session_id;
}
