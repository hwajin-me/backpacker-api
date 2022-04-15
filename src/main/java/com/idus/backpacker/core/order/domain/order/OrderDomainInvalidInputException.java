package com.idus.backpacker.core.order.domain.order;

import com.idus.backpacker.core.kernel.domain.share.DomainInvalidInputException;

public class OrderDomainInvalidInputException extends DomainInvalidInputException {
    public OrderDomainInvalidInputException(String message, String field) {
        super(message, field);
    }
}
