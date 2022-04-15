package com.idus.backpacker.core.user.domain.user;

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
public class UserId implements Serializable {
    @NonNull
    @Column(name = "id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID value;

    public static UserId of(@NonNull UUID value) {
        return new UserId(value);
    }

    @NonNull
    public UUID toUuid() {
        return this.value;
    }
}
