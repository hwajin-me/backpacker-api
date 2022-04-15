package com.idus.backpacker.core.user.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.idus.backpacker.core.kernel.domain.share.Email;
import com.idus.backpacker.core.kernel.domain.share.PhoneNumber;
import com.idus.backpacker.core.user.domain.share.OrderId;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User > Domain > User > User AggregateRoot 테스트")
class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user =
                new User(
                        UserId.of(UUID.randomUUID()),
                        UserName.of("이름"),
                        UserNickName.of("test"),
                        "pw",
                        Email.of("test@gmail.com"),
                        PhoneNumber.of("01011112222"),
                        null);
    }

    @Test
    @DisplayName("유저 애그리게잇을 만들 수 있어야 한다")
    void should_be_able_to_create_user_aggregate_root() {
        assertThat(user).isNotNull().isInstanceOf(User.class);
    }

    @Test
    @DisplayName("유저 애그리게잇에 마지막 주문 정보를 기록할 수 있어야 한다")
    void should_be_able_to_update_user_order_info() {
        user.recordOrder(
                UserOrder.of(
                        OrderId.of(UUID.randomUUID()), "CODE", "NAME", LocalDateTime.of(2022, 1, 1, 0, 0, 0)));

        assertThat(user.getOrder()).isNotNull().isInstanceOf(UserOrder.class);
    }
}
