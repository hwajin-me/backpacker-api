package com.idus.backpacker.api.user.application.exception;

public class UserNotFoundException extends UserApplicationLogicException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
