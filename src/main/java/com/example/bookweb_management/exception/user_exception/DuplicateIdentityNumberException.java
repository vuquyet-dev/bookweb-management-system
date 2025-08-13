package com.example.bookweb_management.exception.user_exception;

public class DuplicateIdentityNumberException extends RuntimeException {
    public DuplicateIdentityNumberException(String message) {
        super(message);
    }
}
