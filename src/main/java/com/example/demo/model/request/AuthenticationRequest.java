package com.example.demo.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotEmpty
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "Invalid email address!")
    private String email;
    @NotEmpty
    @Size(message = "minimum 8 letters", min = 8, max = 15)
    private String password;
}
