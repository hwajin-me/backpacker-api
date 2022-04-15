package com.idus.backpacker.api.user.application.query;

import com.idus.backpacker.api.kernel.application.query.Query;
import com.idus.backpacker.api.user.application.exception.UserNotFoundException;
import com.idus.backpacker.api.user.application.query.field.FindUserField;
import com.idus.backpacker.api.user.application.query.result.UserResult;
import com.idus.backpacker.api.user.application.query.result.UsersResult;
import com.idus.backpacker.core.user.domain.user.UserId;
import com.idus.backpacker.core.user.domain.user.UserInformation;
import com.idus.backpacker.core.user.domain.user.UserRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Query
@RequiredArgsConstructor
public class UserQuery {
    private final UserRepository repository;
    private final UserSupport support;

    @NonNull
    public UserResult findUserBy(@NonNull UUID userId) {
        return this.repository
                .findById(UserId.of(userId))
                .map(
                        user ->
                                UserResult.builder()
                                        .id(user.getId())
                                        .name(user.getName())
                                        .nickName(user.getNickName())
                                        .email(user.getEmail())
                                        .phoneNumber(user.getPhoneNumber())
                                        .createdAt(user.getCreatedAt())
                                        .updatedAt(user.getUpdatedAt())
                                        .sex(
                                                Optional.ofNullable(user.getInformation())
                                                        .map(UserInformation::getSex)
                                                        .orElse(null))
                                        .order(
                                                Optional.ofNullable(user.getOrder())
                                                        .map(
                                                                order ->
                                                                        UserResult.Order.builder()
                                                                                .orderId(order.getOrderId())
                                                                                .code(order.getCode())
                                                                                .productName(order.getProductName())
                                                                                .orderedAt(order.getOrderedAt())
                                                                                .build())
                                                        .orElse(null))
                                        .build())
                .orElseThrow(() -> new UserNotFoundException(String.format("회원 %s 는 존재하지 않습니다.", userId)));
    }

    @NonNull
    public Page<UsersResult> findBy(@NonNull FindUserField field, @NonNull Pageable pageable) {
        return this.support.findBy(field, pageable);
    }
}
