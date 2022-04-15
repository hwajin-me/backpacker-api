package com.idus.backpacker.core.order.domain.order;

import com.idus.backpacker.core.kernel.domain.share.DomainLogicException;

public class OrderDomainLogicException extends DomainLogicException {
    OrderDomainLogicException(String message) {
        super(message);
    }
}
