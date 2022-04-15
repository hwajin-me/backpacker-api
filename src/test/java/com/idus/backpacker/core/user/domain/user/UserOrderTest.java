package com.idus.backpacker.core.user.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.idus.backpacker.core.user.domain.share.OrderId;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User > Domain > User > UserOrder 테스트")
class UserOrderTest {
    private UUID orderId;
    private UserOrder order;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        order =
                UserOrder.of(OrderId.of(orderId), "TEST", "TEST", LocalDateTime.of(2000, 1, 1, 0, 0, 0));
    }

    @Test
    @DisplayName("유저의 마지막 주문 정보를 만들 수 있어야 한다")
    void should_be_able_to_create_UserOrder() {
        assertThat(order).isNotNull().isInstanceOf(UserOrder.class);
    }

    @Test
    @DisplayName("유저의 마지막 주문 정보는 동등하게 비교되어야 한다")
    void should_be_able_to_compare_by_values() {
        var bar =
                UserOrder.of(OrderId.of(orderId), "TEST", "TEST", LocalDateTime.of(2000, 1, 1, 0, 0, 0));

        assertThat(bar).isEqualTo(order);
    }
}
