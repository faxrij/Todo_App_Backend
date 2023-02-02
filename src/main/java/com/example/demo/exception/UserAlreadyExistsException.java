package com.example.demo.exception;

public class UserAlreadyExistsException extends BusinessException {

    public UserAlreadyExistsException(String message) {
        super(ErrorCode.user_already_exists, message);
    }
}