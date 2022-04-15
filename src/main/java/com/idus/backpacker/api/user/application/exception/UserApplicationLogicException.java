package com.idus.backpacker.api.user.application.exception;

import com.idus.backpacker.api.kernel.application.exception.ApplicationLogicException;

public class UserApplicationLogicException extends ApplicationLogicException {
    public UserApplicationLogicException(String message) {
        super(message);
    }
}
