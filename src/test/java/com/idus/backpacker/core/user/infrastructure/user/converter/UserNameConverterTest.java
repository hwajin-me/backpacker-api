package com.idus.backpacker.core.user.infrastructure.user.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.idus.backpacker.core.user.domain.user.UserName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User > Infra > User > Converter > UserNameConverter 테스트")
class UserNameConverterTest {
    private UserNameConverter converter;

    @BeforeEach
    void setUp() {
        this.converter = new UserNameConverter();
    }

    @Test
    @DisplayName("DB 값으로 변환할 때 String 으로 변경되어야 한다")
    void should_be_convert_by_string() {
        var input = UserName.of("가나다");

        var result = this.converter.convertToDatabaseColumn(input);

        assertThat(result).isEqualTo("가나다");
    }

    @Test
    @DisplayName("DB 값으로 변환할 때 값이 NULL 이라면 반환 값도 NULL이어야 한다")
    void should_be_able_to_convert_string_but_return_null() {
        assertThat(this.converter.convertToDatabaseColumn(null)).isNull();
    }

    @Test
    @DisplayName("UserName 객체로 변환할 수 있어야 한다")
    void should_be_able_to_convert_UserName_obj_When_call_convertToEntityAttribute() {
        var input = "가나다";

        var result = this.converter.convertToEntityAttribute(input);

        assertThat(result).isEqualTo(UserName.of("가나다"));
    }

    @Test
    @DisplayName("UserName 객체로 변환할 때 값이 NULL이라면 NULL 로 반환해야 한다")
    void should_be_able_to_convert_UserName_but_return_null() {
        assertThat(this.converter.convertToEntityAttribute(null)).isNull();
    }
}
