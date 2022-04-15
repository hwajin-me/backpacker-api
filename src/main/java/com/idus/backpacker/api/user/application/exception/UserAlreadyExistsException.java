package com.idus.backpacker.api.user.application.exception;

public class UserAlreadyExistsException extends UserApplicationLogicException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
