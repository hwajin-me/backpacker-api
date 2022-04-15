package com.idus.backpacker.core.kernel.infrastructure.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.idus.backpacker.core.kernel.domain.share.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Kernel > Infra > Converter > PhoneNumberConverter 테스트")
class PhoneNumberConverterTest {
    private PhoneNumberConverter converter;
    private PhoneNumber phoneNumber;

    @BeforeEach
    void setUp() {
        converter = new PhoneNumberConverter();
        phoneNumber = PhoneNumber.of("01011112222");
    }

    @Test
    @DisplayName("휴대폰 번호를 문자열로 변환할 수 있어야 한다")
    void should_be_able_to_convert_string() {
        assertThat(this.converter.convertToDatabaseColumn(phoneNumber)).isEqualTo("+821011112222");
    }

    @Test
    @DisplayName("휴대폰 번호를 문자열로 변환할 때 값이 NULL 이라면 NULL을 반환해야 한다")
    void should_be_able_to_convert_string_but_return_null() {
        assertThat(this.converter.convertToDatabaseColumn(null)).isNull();
    }

    @Test
    @DisplayName("문자열을 휴대폰 번호로 변환할 수 있어야 한다")
    void should_be_able_to_convert_PhoneNumber() {
        assertThat(this.converter.convertToEntityAttribute("01011112222")).isEqualTo(phoneNumber);
    }

    @Test
    @DisplayName("문자열을 휴대폰 번호로 변환할 때 값이 NULL 이라면 NULL을 반환해야 한다")
    void should_be_able_to_convert_PhoneNumber_but_return_null() {
        assertThat(this.converter.convertToEntityAttribute(null)).isNull();
    }
}
