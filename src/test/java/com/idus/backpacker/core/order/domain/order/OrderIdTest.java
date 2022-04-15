package com.idus.backpacker.core.order.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Order > Domain > Order > OrderId 테스트")
class OrderIdTest {
    @Test
    @DisplayName("주문 아이디는 동등성 비교가 진행되어야 한다")
    void should_be_compare_by_value() {
        var value = "25e7111b-c76a-4c2d-b13b-2bd168d232e0";
        var foo = OrderId.of(UUID.fromString(value));
        var bar = OrderId.of(UUID.fromString(value));

        assertThat(foo).isEqualTo(bar);
    }

    @Test
    @DisplayName("주문 아이디는 UUID로 변환할 수 있어야 한다")
    void should_be_able_to_convert_uuid_When_call_toUuid() {
        var uuid = UUID.randomUUID();
        var foo = OrderId.of(uuid);

        assertThat(foo.toUuid()).isEqualTo(uuid);
    }
}
