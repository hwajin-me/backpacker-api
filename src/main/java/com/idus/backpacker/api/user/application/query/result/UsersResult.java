package com.idus.backpacker.api.user.application.query.result;

import com.idus.backpacker.core.kernel.domain.share.Email;
import com.idus.backpacker.core.kernel.domain.share.PhoneNumber;
import com.idus.backpacker.core.user.domain.share.OrderId;
import com.idus.backpacker.core.user.domain.user.UserId;
import com.idus.backpacker.core.user.domain.user.UserName;
import com.idus.backpacker.core.user.domain.user.UserNickName;
import com.idus.backpacker.core.user.domain.user.UserSex;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class UsersResult {
    @NonNull private UserId id;
    @NonNull private UserName name;
    @NonNull private UserNickName nickName;
    @NonNull private Email email;
    @NonNull private PhoneNumber phoneNumber;
    private UserSex sex;
    private Order order;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter
    public static class Order {
        @NonNull private OrderId orderId;
        @NonNull private String code;
        @NonNull private String productName;
        @NonNull private LocalDateTime orderedAt;
    }
}
