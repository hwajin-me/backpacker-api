package com.idus.backpacker.core.kernel.domain.share;

public class DomainLogicException extends RuntimeException {
    protected DomainLogicException(String message) {
        super(message);
    }

    protected DomainLogicException(String message, Throwable cause) {
        super(message, cause);
    }
}
