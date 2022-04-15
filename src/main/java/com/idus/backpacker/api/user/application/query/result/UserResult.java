package com.idus.backpacker.api.user.application.query.result;

import com.idus.backpacker.core.kernel.domain.share.Email;
import com.idus.backpacker.core.kernel.domain.share.PhoneNumber;
import com.idus.backpacker.core.user.domain.share.OrderId;
import com.idus.backpacker.core.user.domain.user.UserId;
import com.idus.backpacker.core.user.domain.user.UserName;
import com.idus.backpacker.core.user.domain.user.UserNickName;
import com.idus.backpacker.core.user.domain.user.UserSex;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class UserResult {
    @NonNull private final UserId id;

    @NonNull private final UserName name;

    @NonNull private final UserNickName nickName;

    @NonNull private final Email email;

    @NonNull private final PhoneNumber phoneNumber;

    private final UserSex sex;

    private final Order order;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    @Getter
    public static class Order {
        @NonNull private final OrderId orderId;

        @NonNull private final String code;

        @NonNull private final String productName;

        @NonNull private final LocalDateTime orderedAt;
    }
}
