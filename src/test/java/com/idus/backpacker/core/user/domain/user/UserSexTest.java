package com.idus.backpacker.core.user.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User > Domain > User > UserSex 테스트")
class UserSexTest {
    @Test
    @DisplayName("존재하지 않는 성별이라면 에러를 발생시켜야 한다")
    void should_be_raise_an_exception_When_not_found() {
        assertThatThrownBy(() -> UserSex.fromString("UNDEFINED"))
                .isInstanceOf(UserDomainLogicException.class);
    }

    @Test
    @DisplayName("MALE 문자열을 성별로 변환할 수 있어야 한다")
    void should_be_able_to_convert_from_string_MALE() {
        var result = UserSex.fromString("MALE");

        assertThat(result).isEqualTo(UserSex.MALE);
    }

    @Test
    @DisplayName("FEMALE 문자열을 성별로 변환할 수 있어야 한다")
    void should_be_able_to_convert_from_string_FEMALE() {
        var result = UserSex.fromString("FEMALE");

        assertThat(result).isEqualTo(UserSex.FEMALE);
    }
}
