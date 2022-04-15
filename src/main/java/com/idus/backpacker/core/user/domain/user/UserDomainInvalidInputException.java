package com.idus.backpacker.core.user.domain.user;

import com.idus.backpacker.core.kernel.domain.share.DomainInvalidInputException;

public class UserDomainInvalidInputException extends DomainInvalidInputException {
    public UserDomainInvalidInputException(String message, String field) {
        super(message, field);
    }
}
