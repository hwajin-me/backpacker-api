package com.idus.backpacker.core.user.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User > Domain > User > UserNickName 테스트")
class UserNickNameTest {
    private UserNickName name;

    @BeforeEach
    void setUp() {
        name = UserNickName.of("abc");
    }

    @Test
    @DisplayName("닉네임을 만들 수 있어야 한다")
    void should_be_able_to_create_UserNickName() {
        assertThat(name).isInstanceOf(UserNickName.class).isNotNull();
    }

    @Test
    @DisplayName("닉네임을 빈 값으로 만들 수 없어야 한다")
    void should_raise_an_exception_When_create_by_empty_string() {
        assertThatThrownBy(() -> UserNickName.of(""))
                .isInstanceOf(UserDomainInvalidInputException.class);
    }

    @Test
    @DisplayName("닉네임을 공백만으로 만들 수 없어야 한다")
    void should_raise_an_exception_When_create_by_blank() {
        assertThatThrownBy(() -> UserNickName.of("  "))
                .isInstanceOf(UserDomainInvalidInputException.class);
    }

    @Test
    @DisplayName("닉네임을 영문 대문자로 만들 수 없어야 한다")
    void should_raise_an_exception_When_create_by_alphabet_uppercase() {
        assertThatThrownBy(() -> UserNickName.of("UPPERCASEMAN"))
                .isInstanceOf(UserDomainInvalidInputException.class);
    }

    @Test
    @DisplayName("닉네임을 30자 초과해서 만들 수 없어야 한다")
    void should_raise_an_exception_When_create_more_than_30_letters() {
        assertThatThrownBy(() -> UserNickName.of("lkjhdfigjdklfgjlkfdajlkdjfglkdajfglkadfjg"))
                .isInstanceOf(UserDomainInvalidInputException.class);
    }

    @Test
    @DisplayName("닉네임은 동등하게 비교되어야 한다")
    void should_be_able_to_compare_by_value() {
        var bar = UserNickName.of("abc");

        assertThat(bar).isEqualTo(name);
    }

    @Test
    @DisplayName("닉네임을 문자열로 변환할 수 있어야 한다")
    void should_be_able_to_convert_string_When_call_toString() {
        assertThat(name.toString()).isEqualTo("abc");
    }
}
