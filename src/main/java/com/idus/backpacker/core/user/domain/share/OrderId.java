package com.idus.backpacker.core.user.domain.share;

import com.idus.backpacker.core.kernel.domain.share.ValueObject;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@ValueObject
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class OrderId {
    private final UUID value;

    public static OrderId of(@NonNull UUID value) {
        return new OrderId(value);
    }

    @NonNull
    public UUID toUuid() {
        return this.value;
    }
}
