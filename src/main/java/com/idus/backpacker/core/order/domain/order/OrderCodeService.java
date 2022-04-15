package com.idus.backpacker.core.order.domain.order;

import java.time.LocalDateTime;

public interface OrderCodeService {
    /**
     * Generate OrderCode
     *
     * @return OrderCode
     */
    OrderCode generate(LocalDateTime dt);
}
