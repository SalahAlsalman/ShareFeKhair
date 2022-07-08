package com.example.sharefekhair.DTO;

import com.example.sharefekhair.model.MyClass;
import com.example.sharefekhair.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor @Data @NoArgsConstructor
public class MySessionDTO {
@NotNull(message = "class_id is required")
    private UUID class_id;
}
