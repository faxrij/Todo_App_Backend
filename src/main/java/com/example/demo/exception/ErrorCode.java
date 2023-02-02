package com.example.demo.exception;

public enum ErrorCode {

    password_mismatch(401),
    unauthorized(401),
    forbidden(403),
    user_missing(404),
    todo_missing(404),
    user_already_exists(409),
    todo_already_exists(409),
    internal_server_error(500);
    private final int httpCode;

    ErrorCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public int getHttpCode() {
        return httpCode;
    }
}