package com.example.demo.exception;

public class UnathorizedException extends BusinessException {
    public UnathorizedException(String message) {
        super(ErrorCode.unauthorized, message);
    }
}
