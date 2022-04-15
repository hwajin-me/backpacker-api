package com.idus.backpacker.core.kernel.domain.share;

import lombok.Getter;

@Getter
public class DomainInvalidInputException extends DomainLogicException {
    private final String field;

    public DomainInvalidInputException(String message, String field) {
        super(message);
        this.field = field;
    }

    public DomainInvalidInputException(String message, String field, Throwable cause) {
        super(message, cause);
        this.field = field;
    }
}
