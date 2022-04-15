package com.idus.backpacker.api.order.application.query.result;

import com.idus.backpacker.core.order.domain.order.OrderCode;
import com.idus.backpacker.core.order.domain.order.OrderId;
import com.idus.backpacker.core.order.domain.order.OrderProductName;
import com.idus.backpacker.core.order.domain.share.UserId;
import java.time.LocalDateTime;
import lombok.*;

@Getter
public class OrdersResult {
    @NonNull private OrderId id;
    @NonNull private UserId userId;
    @NonNull private OrderCode code;
    @NonNull private OrderProductName productName;
    @NonNull private LocalDateTime orderedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
