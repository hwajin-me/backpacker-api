package com.idus.backpacker.core.user.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User > Domain > User > UserInformation 테스트")
class UserInformationTest {
    private UserInformation info;

    @BeforeEach
    void setUp() {
        info = UserInformation.of(UserSex.MALE);
    }

    @Test
    @DisplayName("유저 상세정보는 동등하게 비교되어야 한다")
    void should_be_able_to_compare_by_value() {
        var bar = UserInformation.of(UserSex.MALE);

        assertThat(bar).isEqualTo(info);
    }
}
