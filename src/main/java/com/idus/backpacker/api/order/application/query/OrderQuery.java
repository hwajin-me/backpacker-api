package com.idus.backpacker.api.order.application.query;

import com.idus.backpacker.api.kernel.application.query.Query;
import com.idus.backpacker.api.order.application.query.field.OrderField;
import com.idus.backpacker.api.order.application.query.result.OrdersResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Query
@RequiredArgsConstructor
public class OrderQuery {
    private final OrderSupport support;

    @NonNull
    public Page<OrdersResult> findBy(@NonNull OrderField field, @NonNull Pageable pageable) {
        return this.support.findBy(field, pageable);
    }
}
