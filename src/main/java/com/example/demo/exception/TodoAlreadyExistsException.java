package com.example.demo.exception;

public class TodoAlreadyExistsException extends BusinessException {
    public TodoAlreadyExistsException(String message) {
        super(ErrorCode.todo_already_exists, message);
    }
}
