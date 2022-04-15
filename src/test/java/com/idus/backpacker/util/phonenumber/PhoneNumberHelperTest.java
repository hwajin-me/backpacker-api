package com.idus.backpacker.util.phonenumber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Util > Phonenumber > PhoneNumberHelper 테스트")
class PhoneNumberHelperTest {
    private final String number = "010-1234-5678";

    @Test
    @DisplayName("번호를 파싱할 수 있어야 한다")
    void should_be_able_to_parse_number() {
        assertThat(PhoneNumberHelper.format(number)).isEqualTo("+821012345678");
    }

    @Test
    @DisplayName("국소적으로 이용하는 로컬 번호를 검증할 수 있어야 한다")
    void should_be_able_to_validate_local_number() {
        var result = PhoneNumberHelper.isValidReason("112");

        assertThat(result).isEqualTo(PhoneNumberUtil.ValidationResult.IS_POSSIBLE);
    }

    @Test
    @DisplayName("너무 짧아 검증에 실패한 번호의 이유를 받아볼 수 있어야 한다")
    void should_be_able_validation_result() {
        var result = PhoneNumberHelper.isValidReason("012312o38937427384");

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("한국 국가 코드가 있는 번호를 파싱할 수 있어야 한다")
    void should_be_able_to_parse_kr_number() {
        var result = PhoneNumberHelper.format("+82 10-1234-5678");

        assertThat(result).isEqualTo("+821012345678");
    }

    @Test
    @DisplayName("번호의 검증을 할 수 있어야 한다")
    void should_be_able_to_valid_number() {
        assertThat(PhoneNumberHelper.isValid(number)).isTrue();
    }

    @Test
    @DisplayName("번호의 검증 결과를 받아볼 수 있어야 한다")
    void should_be_able_to_get_validation_result() {
        assertThat(PhoneNumberHelper.isValidReason(number))
                .isEqualTo(PhoneNumberUtil.ValidationResult.IS_POSSIBLE);
    }

    @Test
    @DisplayName("파싱에 실패하였을 때 에러가 발생해야 한다")
    void should_raise_an_exception_When_parse_failed() {
        var number = "12312312301231231231231231231";

        assertThatThrownBy(() -> PhoneNumberHelper.format(number))
                .isInstanceOf(PhoneNumberHelperException.class);
    }
}
