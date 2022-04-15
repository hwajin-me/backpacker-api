package com.idus.backpacker.core.order.infrastructure.order.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.idus.backpacker.core.order.domain.order.OrderCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Order > Infra > Order > Converter > OrderCodeConverter 테스트")
class OrderCodeConverterTest {
    private OrderCodeConverter converter;
    private OrderCode code;

    @BeforeEach
    void setUp() {
        converter = new OrderCodeConverter();
        code = OrderCode.of("AZSXDCFVGBHN");
    }

    @Test
    @DisplayName("주문 번호를 문자열로 변경할 수 있어야 한다")
    void should_be_able_to_string_When_call_convertDb() {
        assertThat(this.converter.convertToDatabaseColumn(code)).isEqualTo("AZSXDCFVGBHN");
    }

    @Test
    @DisplayName("주문 번호가 NULL 이라면 문자열로 치환하였을 때 NULL을 반환해야 한다")
    void should_be_able_to_string_but_return_null_When_call_convertDb() {
        assertThat(this.converter.convertToDatabaseColumn(null)).isNull();
    }

    @Test
    @DisplayName("문자열을 주문 번호로 변환할 수 있어야 한다")
    void should_be_able_to_convert_string_to_OrderId() {
        assertThat(this.converter.convertToEntityAttribute("AZSXDCFVGBHN")).isEqualTo(code);
    }

    @Test
    @DisplayName("문자열을 주문 번호로 치환할 때 NULL 이라면 치환 후 값도 NULL 이 되어야 한다")
    void should_be_able_to_convert_OrderCode_but_return_null() {
        assertThat(this.converter.convertToEntityAttribute(null)).isNull();
    }
}
