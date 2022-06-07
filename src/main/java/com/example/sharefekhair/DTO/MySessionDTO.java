package com.example.sharefekhair.DTO;

import com.example.sharefekhair.model.MyClass;
import com.example.sharefekhair.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor @Data
public class MySessionDTO {

    @NotNull(message = "teacher_id is required")
    private Integer teacher_id;

    @NotNull(message = "class_id is required")
    private Integer class_id;
}
