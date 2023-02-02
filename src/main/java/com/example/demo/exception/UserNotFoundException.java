package com.example.demo.exception;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String message) {
        super(ErrorCode.user_missing, message);
    }
}