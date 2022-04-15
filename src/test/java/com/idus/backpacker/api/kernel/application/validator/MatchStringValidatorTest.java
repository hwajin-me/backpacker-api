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
@DisplayName("Kernel > App > Validator > MatchStringValidator 테스트")
class MatchStringValidatorTest {
    private MatchStringValidator validator;

    @Mock private ConstraintValidatorContext context;

    @Mock private MatchString annotation;

    @BeforeEach
    void setUp() {
        validator = new MatchStringValidator();
    }

    @Test
    @DisplayName("A,B 를 허용하였을 때 A를 검증한다면 검증에 통과해야 한다")
    void should_be_able_to_return_true_When_validate_A_items_AB() {
        when(annotation.allows()).thenReturn(new String[] {"A", "B"});

        this.validator.initialize(annotation);
        var result = this.validator.isValid("A", context);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("A,B 를 허용하였을 때 C를 검증한다면 검증에 실패해야 한다")
    void should_be_add_error_When_validate_C_AB() {
        var builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        given(context.buildConstraintViolationWithTemplate(any())).willReturn(builder);

        when(annotation.allows()).thenReturn(new String[] {"A", "B"});

        this.validator.initialize(annotation);
        var result = this.validator.isValid("C", context);

        assertThat(result).isFalse();
        then(builder).should(atLeastOnce()).addConstraintViolation();
    }

    @Test
    @DisplayName("NULL 을 허용하였을 때 NULL을 검증하면 검증에 통과해야 한다")
    void should_be_able_to_return_true_When_validate_NULL_nullable() {
        when(annotation.nullable()).thenReturn(true);
        when(annotation.allows()).thenReturn(new String[] {});

        this.validator.initialize(annotation);
        var result = this.validator.isValid(null, context);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("NULL 을 허용하지 않았을 때 NULL 을 검증하면 에러 컬렉션에 에러가 담겨야 한다")
    void should_be_add_error_When_validate_NULL_nonNull() {
        var builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        given(context.buildConstraintViolationWithTemplate(any())).willReturn(builder);

        when(annotation.nullable()).thenReturn(false);
        when(annotation.allows()).thenReturn(new String[] {});

        this.validator.initialize(annotation);
        var result = this.validator.isValid(null, context);

        assertThat(result).isFalse();
        then(builder).should(atLeastOnce()).addConstraintViolation();
    }
}
