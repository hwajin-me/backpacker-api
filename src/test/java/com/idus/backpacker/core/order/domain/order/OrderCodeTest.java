package com.idus.backpacker.core.order.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Order > Domain > Order > OrderCode 테스트")
class OrderCodeTest {

    @Test
    @DisplayName("주문 번호는 동등성으로 비교되어야 한다")
    void should_be_compare_by_value() {
        var foo = OrderCode.of("111111111111");
        var bar = OrderCode.of("111111111111");

        assertThat(foo).isEqualTo(bar).isNotNull();
    }

    @Test
    @DisplayName("주문 번호는 12자리의 문자로 만들 수 있어야 한다")
    void should_be_able_to_create_12_letters() {
        var foo = OrderCode.of("123456789012");

        assertThat(foo).isNotNull().isInstanceOf(OrderCode.class);
    }

    @Test
    @DisplayName("주문 번호는 12자리 미만일 때 생성할 수 없어야 한다")
    void should_raise_an_exception_When_less_than_12_letters() {
        assertThatThrownBy(() -> OrderCode.of("123456789"))
                .isInstanceOf(OrderDomainLogicException.class);
    }

    @Test
    @DisplayName("주문 번호는 12자리를 초과했을 때 생성할 수 없어야 한다")
    void should_raise_an_exception_When_more_than_12_letters() {
        assertThatThrownBy(() -> OrderCode.of("AAAAAAAAAAAAAAAA"))
                .isInstanceOf(OrderDomainLogicException.class);
    }

    @Test
    @DisplayName("주문 번호에는 영문 대문자와 숫자가 들어갈 수 있어야 한다")
    void should_be_able_to_create_alphanumeric_uppercase() {
        var foo = OrderCode.of("ABCDEFG12345");

        assertThat(foo).isNotNull().isInstanceOf(OrderCode.class);
    }

    @Test
    @DisplayName("주문 번호에는 영문 소문자가 들어갈 수 없어야 한다")
    void should_raise_an_exception_When_alphabet_lower() {
        assertThatThrownBy(() -> OrderCode.of("zacdvfbgnhmj"))
                .isInstanceOf(OrderDomainLogicException.class);
    }

    @Test
    @DisplayName("주문 번호에는 특수문자가 들어갈 수 없어야 한다")
    void should_raise_an_exception_When_special_character() {
        assertThatThrownBy(() -> OrderCode.of("1AZSXDCFVGB "))
                .isInstanceOf(OrderDomainLogicException.class);
    }

    @Test
    @DisplayName("주문 번호의 문자 변환은 객체 정보 없이 주문 번호 자체만 나올 수 있어야 한다")
    void should_be_able_to_get_plain_value_When_call_toString() {
        var foo = OrderCode.of("AZSXDCFVGBHN");

        assertThat(foo.toString()).isEqualTo("AZSXDCFVGBHN");
    }
}
