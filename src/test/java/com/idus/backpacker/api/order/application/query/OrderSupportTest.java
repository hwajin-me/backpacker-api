package com.idus.backpacker.api.order.application.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.idus.backpacker.api.BackpackerApiApplicationTest;
import com.idus.backpacker.api.order.application.query.field.OrderField;
import com.idus.backpacker.api.order.application.query.result.OrdersResult;
import com.idus.backpacker.core.order.domain.order.*;
import com.idus.backpacker.core.order.domain.share.UserId;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@DisplayName("Order > App > Query > OrderSupport 테스트")
class OrderSupportTest extends BackpackerApiApplicationTest {
    @Autowired private OrderSupport support;

    @Autowired private OrderRepository repository;

    @BeforeEach
    void setUp() {
        this.repository.save(
                Order.of(
                        OrderId.of(UUID.fromString("1a3ea279-bfe3-43ce-9764-a66effa2574f")),
                        UserId.of(UUID.fromString("1a3ea279-bfe3-43ce-9764-a66effa2574f")),
                        OrderCode.of("2222222222"),
                        OrderProductName.of("PRODUCT_NAME"),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0)));
    }

    @Test
    @DisplayName("회원 아이디 필터에 부합하는 원소가 있다면 해당 원소를 담은 집합을 반환할 수 있어야 한다")
    void should_be_able_to_return_items() {
        var result =
                this.support.findBy(
                        OrderField.builder()
                                .userId(UUID.fromString("1a3ea279-bfe3-43ce-9764-a66effa2574f"))
                                .build(),
                        PageRequest.ofSize(1));

        assertThat(result.getContent()).isNotEmpty().first().isInstanceOf(OrdersResult.class);
    }

    @Test
    @DisplayName("회원 아이디 필터에 부합하는 원소가 없다면 빈 집합을 반환해야 한다")
    void should_be_able_to_return_empty_collection() {
        var result =
                this.support.findBy(
                        OrderField.builder()
                                .userId(UUID.fromString("2a3ea279-bfe3-43ce-9764-a66effa2574f"))
                                .build(),
                        PageRequest.ofSize(1));

        assertThat(result.getContent()).isEmpty();
    }
}
