package com.idus.backpacker.core.order.domain.order;

import com.idus.backpacker.core.order.domain.share.UserId;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Ordered {
    @NonNull private final OrderId orderId;

    @NonNull private final UserId userId;

    @NonNull private final LocalDateTime orderedAt;

    public static Ordered of(
            @NonNull OrderId orderId, @NonNull UserId userId, @NonNull LocalDateTime orderedAt) {
        return builder().orderId(orderId).userId(userId).orderedAt(orderedAt).build();
    }
}
