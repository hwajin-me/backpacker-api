package com.idus.backpacker.core.user.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User > Domain > User > UserName 테스트")
class UserNameTest {
    private UserName name;

    @BeforeEach
    void setUp() {
        name = UserName.of("가나다");
    }

    @Test
    @DisplayName("유저 이름을 한글로 만들 수 있어야 한다")
    void should_be_able_to_create_UserName_by_korean_alphabet() {
        assertThat(name).isInstanceOf(UserName.class).isNotNull();
    }

    @Test
    @DisplayName("유저 이름은 한글 자음 또는 모음만으로 구성할 수 없어야 한다")
    void should_raise_an_exception_When_korean_alphabet_without_complete_char() {
        assertThatThrownBy(() -> UserName.of("ㄱㅏㄴㅏㄷㅏ"))
                .isInstanceOf(UserDomainInvalidInputException.class);
    }

    @Test
    @DisplayName("유저 이름은 빈 값을 입력할 수 없어야 한다")
    void should_raise_an_exception_When_empty_string() {
        assertThatThrownBy(() -> UserName.of(" ")).isInstanceOf(UserDomainInvalidInputException.class);
    }

    @Test
    @DisplayName("유저 이름은 영문 대문자를 포함할 수 있어야 한다")
    void should_be_able_to_create_UserName_by_alphabet_uppercase() {
        assertThat(UserName.of("ALPHA")).isNotNull().isInstanceOf(UserName.class);
    }

    @Test
    @DisplayName("유저 이름은 영문 소문자를 포함할 수 있어야 한다")
    void should_be_able_to_create_UserName_by_alphabet_lowercase() {
        assertThat(UserName.of("alphabet")).isNotNull().isInstanceOf(UserName.class);
    }

    @Test
    @DisplayName("유저 이름은 문자열로 변환할 수 있어야 한다")
    void should_be_able_to_convert_string_When_call_toString() {
        assertThat(name.toString()).isEqualTo("가나다");
    }

    @Test
    @DisplayName("유저 이름을 동등하게 비교할 수 있어야 한다")
    void should_be_able_to_compare_by_value() {
        var foo = UserName.of("가나다");
        var bar = UserName.of("가나다");

        assertThat(foo).isEqualTo(bar);
    }
}
