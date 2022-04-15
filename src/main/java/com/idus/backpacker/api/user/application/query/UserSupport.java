package com.idus.backpacker.api.user.application.query;

import static com.idus.backpacker.core.user.domain.user.QUser.user;
import static com.idus.backpacker.core.user.domain.user.QUserInformation.userInformation;
import static com.idus.backpacker.core.user.domain.user.QUserOrder.userOrder;

import com.idus.backpacker.api.kernel.application.query.BaseQuerySupport;
import com.idus.backpacker.api.kernel.application.query.QuerySupport;
import com.idus.backpacker.api.user.application.query.field.FindUserField;
import com.idus.backpacker.api.user.application.query.result.UsersResult;
import com.idus.backpacker.core.user.domain.user.User;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@QuerySupport
@Repository
public class UserSupport extends BaseQuerySupport<User> {
    public UserSupport() {
        super(User.class);
    }

    /**
     * 유저를 조건에 맞게 찾는다. 기본 정렬은 수정일시의 최신순 정렬이다.
     *
     * @param field Users Filter Fields
     * @param pageable Page, Sort
     * @return UsersResult
     */
    Page<UsersResult> findBy(@NonNull FindUserField field, @NonNull Pageable pageable) {
        final var query =
                from(user)
                        .leftJoin(user.information, userInformation)
                        .leftJoin(user.order, userOrder)
                        .where(
                                this.expressionNameContains(field.getName()),
                                this.expressionEmailContains(field.getEmail()))
                        .select(
                                Projections.fields(
                                        UsersResult.class,
                                        user.id,
                                        user.name,
                                        user.nickName,
                                        user.email,
                                        user.phoneNumber,
                                        user.createdAt,
                                        user.updatedAt,
                                        user.information.sex.as("sex"),
                                        ExpressionUtils.as(
                                                Projections.fields(
                                                                UsersResult.Order.class,
                                                                user.order.orderId,
                                                                user.order.code,
                                                                user.order.productName,
                                                                user.order.orderedAt)
                                                        .skipNulls(),
                                                "order")))
                        .orderBy(user.updatedAt.desc());

        final var count = query.fetchCount();
        final var result = this.getSupport().applyPagination(pageable, query).fetch();

        return new PageImpl<>(result, pageable, count);
    }

    private BooleanExpression expressionNameContains(String name) {
        return name == null
                ? null
                : Expressions.booleanTemplate("{0} like concat('%', {1}, '%')", user.name, name);
    }

    private BooleanExpression expressionEmailContains(String email) {
        return email == null
                ? null
                : Expressions.booleanTemplate("{0} like concat('%', {1}, '%')", user.email, email);
    }
}
