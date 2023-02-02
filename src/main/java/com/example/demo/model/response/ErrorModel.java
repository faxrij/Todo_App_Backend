package com.example.demo.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorModel {

    private int statusCode;
    private String errorCode;
    private String message;
}
