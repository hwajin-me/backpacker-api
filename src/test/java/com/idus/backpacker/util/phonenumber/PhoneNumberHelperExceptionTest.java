package com.idus.backpacker.util.phonenumber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Util > Phonenumber > PhoneNumberHelperException 테스트")
class PhoneNumberHelperExceptionTest {
    private PhoneNumberHelperException exception;

    @BeforeEach
    void setUp() {
        exception = new PhoneNumberHelperException("TEST");
    }

    @Test
    @DisplayName("객체를 생성할 수 있어야 한다")
    void should_be_able_to_create_exception() {
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("객체를 생성할 때 과거에 발생한 Exception 을 받을 수 있어야 한다")
    void should_be_able_to_create_exception_with_cause() {
        exception = new PhoneNumberHelperException("test", new RuntimeException());

        assertThat(exception).isInstanceOf(RuntimeException.class);
    }
}
