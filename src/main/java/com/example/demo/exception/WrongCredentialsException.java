package com.example.demo.exception;

public class WrongCredentialsException extends BusinessException {
    public WrongCredentialsException(String message) {
        super(ErrorCode.password_mismatch, message);
    }
}
