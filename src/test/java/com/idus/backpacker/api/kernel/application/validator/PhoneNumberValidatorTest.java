package com.idus.backpacker.api.kernel.application.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
@DisplayName("Kernel > App > Validator > PhoneNumberValidator 테스트")
class PhoneNumberValidatorTest {
    private PhoneNumberValidator validator;

    @Mock private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new PhoneNumberValidator();
    }

    @Test
    @DisplayName("정상적인 E.164 휴대폰 번호라면 검증에 통과해야 한다")
    void should_be_return_true_When_validate_E164_phone_number() {
        var result = validator.isValid("+821012345678", context);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("E164 번호가 아닌 문자라면 검증에 실패해야 한다")
    void should_be_add_error_When_validate_non_e164_string() {
        var builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        given(context.buildConstraintViolationWithTemplate(any())).willReturn(builder);

        var result = this.validator.isValid("123123112312312323", context);

        assertThat(result).isFalse();
        then(builder).should(atLeastOnce()).addConstraintViolation();
    }
}
