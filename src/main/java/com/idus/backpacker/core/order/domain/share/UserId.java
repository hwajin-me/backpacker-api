package com.idus.backpacker.core.order.domain.share;

import com.idus.backpacker.core.kernel.domain.share.ValueObject;
import java.util.UUID;
import javax.persistence.Column;
import lombok.*;

@ValueObject
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString(includeFieldNames = false)
public class UserId {
    @Column(name = "user_id", nullable = false)
    private UUID value;

    public static UserId of(@NonNull UUID value) {
        return new UserId(value);
    }

    @NonNull
    public UUID toUuid() {
        return this.value;
    }
}
