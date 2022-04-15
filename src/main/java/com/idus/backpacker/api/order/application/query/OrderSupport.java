package com.idus.backpacker.api.order.application.query;

import static com.idus.backpacker.core.order.domain.order.QOrder.order;

import com.idus.backpacker.api.kernel.application.query.BaseQuerySupport;
import com.idus.backpacker.api.kernel.application.query.QuerySupport;
import com.idus.backpacker.api.order.application.query.field.OrderField;
import com.idus.backpacker.api.order.application.query.result.OrdersResult;
import com.idus.backpacker.core.order.domain.order.Order;
import com.idus.backpacker.core.order.domain.share.UserId;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@QuerySupport
public class OrderSupport extends BaseQuerySupport<Order> {
    public OrderSupport() {
        super(Order.class);
    }

    @NonNull
    Page<OrdersResult> findBy(@NonNull OrderField field, @NonNull Pageable pageable) {
        final var query =
                from(order)
                        .where(this.expressionUserIdEqualTo(UserId.of(field.getUserId())))
                        .orderBy(order.updatedAt.desc())
                        .select(
                                Projections.fields(
                                        OrdersResult.class,
                                        order.id,
                                        order.userId,
                                        order.productName,
                                        order.code,
                                        order.orderedAt,
                                        order.createdAt,
                                        order.updatedAt));

        final var count = query.fetchCount();
        final var result = this.getSupport().applyPagination(pageable, query).fetch();

        return new PageImpl<>(result, pageable, count);
    }

    private BooleanExpression expressionUserIdEqualTo(UserId userId) {
        return userId == null ? null : order.userId.eq(userId);
    }
}
