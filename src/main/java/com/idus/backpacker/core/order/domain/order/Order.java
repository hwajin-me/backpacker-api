package com.idus.backpacker.core.order.domain.order;

import com.idus.backpacker.core.kernel.domain.share.AggregateRoot;
import com.idus.backpacker.core.kernel.domain.share.BaseAggregateRoot;
import com.idus.backpacker.core.order.domain.share.UserId;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

@AggregateRoot
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order_bcs_orders")
public class Order extends BaseAggregateRoot<Order, OrderId> {
    @NonNull @Embedded private UserId userId;

    @NonNull
    @Column(name = "code", nullable = false, length = 12)
    private OrderCode code;

    @NonNull
    @Column(name = "product_name", nullable = false, length = 100)
    private OrderProductName productName;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime orderedAt;

    public static Order of(
            @NonNull OrderId id,
            @NonNull UserId userId,
            @NonNull OrderCode code,
            @NonNull OrderProductName productName,
            @NonNull LocalDateTime orderedAt) {
        return new Order(id, userId, code, productName, orderedAt);
    }

    Order(
            @NonNull OrderId id,
            @NonNull UserId userId,
            @NonNull OrderCode code,
            @NonNull OrderProductName productName,
            @NonNull LocalDateTime orderedAt) {
        super(id);
        this.userId = userId;
        this.code = code;
        this.productName = productName;
        this.orderedAt = orderedAt;

        registerEvent(Ordered.of(this.id, this.userId, this.orderedAt));
    }
}
