package com.example.demo.model.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Getter
public class TodoForm {
    @NotEmpty
    @Size(message = "minimum 4 letters", min = 4, max = 15)
    private String name;
    @NotEmpty
    @Size(message = "minimum 8 letters", min = 8, max = 20)
    private String description;
    private boolean done;
    @NotEmpty
    private float duration;

}