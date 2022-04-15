package com.idus.backpacker.core.user.infrastructure.user.service;

import com.idus.backpacker.core.kernel.infrastructure.exception.InfrastructureLogicException;

public class UserJwtServiceException extends InfrastructureLogicException {
    public UserJwtServiceException(String message) {
        super(message);
    }

    public UserJwtServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
