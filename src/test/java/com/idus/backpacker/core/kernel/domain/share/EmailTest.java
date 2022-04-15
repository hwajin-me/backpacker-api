package com.idus.backpacker.core.kernel.domain.share;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Kernel > Domain > Share > Email 테스트")
class EmailTest {
    private Email email;

    @BeforeEach
    void setUp() {
        email = Email.of("test@gmail.com");
    }

    @Test
    @DisplayName("이메일 VO를 생성할 수 있어야 한다")
    void should_be_able_to_create_vo() {
        assertThat(email).isNotNull();
    }

    @Test
    @DisplayName("이메일 주소에 @가 없으면 에러가 발생해야 한다")
    void should_raise_an_exception_When_invalid_email_cause_no_at() {
        assertThatThrownBy(() -> Email.of("test.gmail.com"))
                .isInstanceOf(DomainInvalidInputException.class);
    }

    @Test
    @DisplayName("이메일 주소의 개인 정보에 .가 있더라도 객체를 만들 수 있어야 한다")
    void should_be_able_to_create_vo_has_dot() {
        email = Email.of("test.auth@naver.com");

        assertThat(email).isNotNull();
    }

    @Test
    @DisplayName("이메일 주소의 개인 정보에 _ 가 있더라도 객체를 만들 수 있어야 한다")
    void should_be_able_to_create_vo_has_underbar() {

        email = Email.of("test_@naver.com");

        assertThat(email).isNotNull();
    }

    @Test
    @DisplayName("이메일 주소의 개인 정보에 특수문자 #가 있다면 에러가 발생해야 한다")
    void should_raise_an_exception_When_email_contains_hash() {
        assertThatThrownBy(() -> Email.of("test#test@naver.com"))
                .isInstanceOf(DomainInvalidInputException.class);
    }

    @Test
    @DisplayName("이메일 주소의 개인 정보에 특수문자 !가 있다면 에러가 발생해야 한다")
    void should_raise_an_exception_When_email_contains_exclamation_mark() {
        assertThatThrownBy(() -> Email.of("test!test@naver.com"))
                .isInstanceOf(DomainInvalidInputException.class);
    }

    @Test
    @DisplayName("이메일 주소에 개인 정보가 없다면 에러가 발생해야 한다")
    void should_raise_an_exception_When_email_d_not_contain_personal_part() {
        assertThatThrownBy(() -> Email.of("@naver.com"))
                .isInstanceOf(DomainInvalidInputException.class);
    }

    @Test
    @DisplayName("이메일 주소에 호스트 정보가 없다면 에러가 발생해야 한다")
    void should_raise_an_exception_When_email_d_not_contain_host() {
        assertThatThrownBy(() -> Email.of("test@")).isInstanceOf(DomainInvalidInputException.class);
    }

    @Test
    @DisplayName("객체를 비교할 때 동등성 비교를 진행해야 한다")
    void should_be_compare_by_equals() {
        var foo = Email.of("test@gmail.com");
        var bar = Email.of("test@gmail.com");

        assertThat(foo).isEqualTo(bar);
    }
}
