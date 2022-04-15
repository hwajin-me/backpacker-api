package com.idus.backpacker.core.user.infrastructure.user.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.idus.backpacker.core.user.domain.user.UserNickName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User > Infra > User > Converter > UserNickNameConverter")
class UserNickNameConverterTest {
    private UserNickNameConverter converter;
    private UserNickName name;

    @BeforeEach
    void setUp() {
        converter = new UserNickNameConverter();
        name = UserNickName.of("test");
    }

    @Test
    @DisplayName("유저 닉네임을 문자열로 변환할 수 있어야 한다")
    void should_be_able_to_convert_string() {
        assertThat(this.converter.convertToDatabaseColumn(name)).isEqualTo("test");
    }

    @Test
    @DisplayName("유저 닉네임을 문자열로 변환할 때 값이 NULL 이라면 NULL을 반환할 수 있어야 한다")
    void should_be_able_to_convert_string_but_return_null() {
        assertThat(this.converter.convertToDatabaseColumn(null)).isNull();
    }

    @Test
    @DisplayName("문자열을 유저 닉네임으로 변환할 수 있어야 한다")
    void should_be_able_to_convert_UserNickName() {
        assertThat(this.converter.convertToEntityAttribute("test")).isEqualTo(name);
    }

    @Test
    @DisplayName("문자열을 유저 닉네임으로 변환할 때 값이 NULL 이라면 NULL을 반환할 수 있어야 한다")
    void should_be_able_to_convert_UserNickName_but_return_null() {
        assertThat(this.converter.convertToEntityAttribute(null)).isNull();
    }
}
