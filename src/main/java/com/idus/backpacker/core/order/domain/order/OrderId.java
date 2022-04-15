package com.idus.backpacker.core.order.domain.order;

import com.idus.backpacker.core.kernel.domain.share.AggregateRootId;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import lombok.*;

@AggregateRootId
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString(includeFieldNames = false)
public class OrderId implements Serializable {
    @NonNull
    @Column(name = "id")
    private UUID value;

    public static OrderId of(@NonNull UUID value) {
        return new OrderId(value);
    }

    public UUID toUuid() {
        return this.value;
    }
}
