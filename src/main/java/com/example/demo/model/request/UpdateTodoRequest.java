package com.example.demo.model.request;

import lombok.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTodoRequest {
    @NotNull(message = "The Name is required.")
    @Size(max = 15, min = 4, message = "todo name should be of 4 letters minimum.")
    private String name;
    @NotNull(message = "The Description is required.")
    @Size(max = 15, min = 8, message = "todo description should be of 8 letters minimum.")
    private String description;
    @NotNull(message = "The Done is required.")
    private boolean done;
    @NotNull(message = "The Duration is required.")
    @Min(value = 1, message = "Todo duration should be at least 1 hour")
    @Max(value = 12, message = "Todo duration should be at most 12 hours")
    private int duration;

}
