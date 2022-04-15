package com.idus.backpacker.api.order.application.query.field;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class OrderField {
    @NonNull private final UUID userId;
}
