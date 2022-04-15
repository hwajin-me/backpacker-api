package com.idus.backpacker.core.kernel.infrastructure.exception;

public class InfrastructureLogicException extends RuntimeException {
    public InfrastructureLogicException(String message) {
        super(message);
    }

    public InfrastructureLogicException(String message, Throwable cause) {
        super(message, cause);
    }
}
