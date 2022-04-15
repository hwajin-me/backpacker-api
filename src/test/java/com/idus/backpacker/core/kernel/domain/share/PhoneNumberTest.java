package com.idus.backpacker.core.kernel.domain.share;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Kernel > Domain > Share > PhoneNumber 테스트")
class PhoneNumberTest {
    private PhoneNumber phoneNumber;

    @BeforeEach
    void setUp() {
        phoneNumber = PhoneNumber.of("010-0000-1111");
    }

    @Test
    @DisplayName("객체를 생성할 수 있어야 한다")
    void should_be_able_to_create_vo() {
        assertThat(phoneNumber).isNotNull();
    }

    @Test
    @DisplayName("객체를 비교할 때 동등성 비교를 진행해야 한다")
    void should_be_compare_by_equals() {
        var foo = PhoneNumber.of("010-0000-1111");
        var bar = PhoneNumber.of("010-0000-1111");

        assertThat(foo).isEqualTo(bar);
    }
}
