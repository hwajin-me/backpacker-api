package com.idus.backpacker.core.order.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Order > Domain > Order > OrderProductName 테스트")
class OrderProductNameTest {
    @Test
    @DisplayName("상품명은 동등하게 비교되어야 한다")
    void should_be_compare_by_value() {
        var foo = OrderProductName.of("1");
        var bar = OrderProductName.of("1");

        assertThat(foo).isEqualTo(bar);
    }

    @Test
    @DisplayName("상품명을 문자열을 통해 만들 수 있어야 한다")
    void should_be_able_to_create_OrderProductName_by_string() {
        assertThat(OrderProductName.of("123")).isNotNull().isInstanceOf(OrderProductName.class);
    }

    @Test
    @DisplayName("상품명은 빈 문자로 만들 수 없어야 한다")
    void should_raise_an_exception_When_emptyString() {
        assertThatThrownBy(() -> OrderProductName.of(""))
                .isInstanceOf(OrderDomainInvalidInputException.class);
    }

    @Test
    @DisplayName("상품명은 공백 문자만으로 만들 수 없어야 한다")
    @Disabled("요구 사항이 없어 제외 (다만 가능성 높아 Disable 처리)")
    void should_raise_an_exception_When_blank() {
        assertThatThrownBy(() -> OrderProductName.of("    "))
                .isInstanceOf(OrderDomainInvalidInputException.class);
    }

    @Test
    @DisplayName("상품명의 문자열 직렬화는 객체 정보를 제외한 이름이 나와야 한다")
    void should_be_able_to_get_plain_product_name() {
        assertThat(OrderProductName.of("1234").toString()).isEqualTo("1234");
    }
}
