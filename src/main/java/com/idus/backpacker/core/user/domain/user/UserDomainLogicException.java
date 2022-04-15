package com.idus.backpacker.core.user.domain.user;

import com.idus.backpacker.core.kernel.domain.share.DomainLogicException;

public class UserDomainLogicException extends DomainLogicException {
    public UserDomainLogicException(String message) {
        super(message);
    }
}
