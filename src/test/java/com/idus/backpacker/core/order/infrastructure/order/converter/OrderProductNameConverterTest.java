package com.idus.backpacker.core.order.infrastructure.order.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.idus.backpacker.core.order.domain.order.OrderProductName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Order > Infra > Order > Converter > OrderProductNameConverter 테스트")
class OrderProductNameConverterTest {
    private OrderProductNameConverter converter;
    private OrderProductName name;

    @BeforeEach
    void setUp() {
        converter = new OrderProductNameConverter();
        name = OrderProductName.of("가나다");
    }

    @Test
    @DisplayName("상품 이름을 문자열로 치환할 수 있어야 한다")
    void should_be_able_to_convert_string() {
        assertThat(this.converter.convertToDatabaseColumn(name)).isEqualTo("가나다");
    }

    @Test
    @DisplayName("상품 이름을 문자열로 치환할 때 값이 NULL 이라면 NULL을 반환할 수 있어야 한다")
    void should_be_able_to_convert_string_but_return_null() {
        assertThat(this.converter.convertToDatabaseColumn(null)).isNull();
    }

    @Test
    @DisplayName("문자열을 상품 이름으로 변환할 수 있어야 한다")
    void should_be_able_to_convert_OrderProductName() {
        assertThat(this.converter.convertToEntityAttribute("가나다")).isEqualTo(name);
    }

    @Test
    @DisplayName("문자열을 상품 이름으로 변환할 때 값이 NULL이라면 NULL 을 반환할 수 있어야 한다")
    void should_be_able_to_convert_OrderProductName_but_return_null() {
        assertThat(this.converter.convertToEntityAttribute(null)).isNull();
    }
}
