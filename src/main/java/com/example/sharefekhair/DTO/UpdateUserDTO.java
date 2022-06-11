package com.example.sharefekhair.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@AllArgsConstructor @Data @NoArgsConstructor
public class UpdateUserDTO {
    @NotEmpty(message = "username is required")
    private String username;
    @NotEmpty(message = "password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotEmpty(message = "email is required")
    @Email(message = "email must be valid")
    private String email;
    @NotEmpty(message = "role is required")
    @Pattern(regexp = "(student|teacher)",message = "role must be (student|teacher)")
    private String role;
}
