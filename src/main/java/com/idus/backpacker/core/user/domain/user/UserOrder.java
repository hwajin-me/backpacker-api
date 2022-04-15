package com.idus.backpacker.core.user.domain.user;

import com.idus.backpacker.core.kernel.domain.share.BaseAggregateRootSubDomain;
import com.idus.backpacker.core.user.domain.share.OrderId;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(
        callSuper = false,
        of = {"orderId", "code", "productName", "orderedAt"})
@ToString
@Entity
@Table(name = "user_bcs_user_orders")
public class UserOrder extends BaseAggregateRootSubDomain<User> {
    @NonNull
    @Column(nullable = false, length = 100, columnDefinition = "BINARY(16)")
    private OrderId orderId;

    @NonNull String code;

    @NonNull @Column String productName;

    @NonNull private LocalDateTime orderedAt;

    public static UserOrder of(
            @NonNull OrderId orderId,
            @NonNull String code,
            @NonNull String productName,
            @NonNull LocalDateTime orderedAt) {
        return new UserOrder(orderId, code, productName, orderedAt);
    }

    private UserOrder(OrderId orderId, String code, String productName, LocalDateTime orderedAt) {
        this.orderId = orderId;
        this.code = code;
        this.productName = productName;
        this.orderedAt = orderedAt;
    }
}
