package com.example.sharefekhair.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class ResponseAPI<T> {
    private T message;
    private Integer status;
}
