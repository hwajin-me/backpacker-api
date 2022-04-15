package com.idus.backpacker.core.order.infrastructure.order.service;

import com.idus.backpacker.core.kernel.infrastructure.exception.InfrastructureLogicException;

public class OrderCodeTimeIncrementServiceException extends InfrastructureLogicException {
    public OrderCodeTimeIncrementServiceException(String message) {
        super(message);
    }
}
