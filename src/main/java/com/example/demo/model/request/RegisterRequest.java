package com.example.demo.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    @Size(message = "minimum 2 letters", min = 2, max = 15)
    private String firstName;
    @NotNull
    @Size(message = "minimum 2 letters", min = 2, max = 15)
    private String lastName;
    @NotNull
    @Email
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "Invalid email address!")
    private String email;
    @NotNull
    @Size(message = "minimum 8 letters", min = 8, max = 15)
    private String password;

}
