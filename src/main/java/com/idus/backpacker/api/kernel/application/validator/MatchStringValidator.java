package com.idus.backpacker.api.kernel.application.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchStringValidator implements ConstraintValidator<MatchString, String> {
    private boolean nullable;
    private List<String> allows = new ArrayList<>();

    @Override
    public void initialize(MatchString constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        this.nullable = constraintAnnotation.nullable();
        this.allows = Arrays.asList(constraintAnnotation.allows());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ((value == null && this.nullable) || this.allows.contains(value)) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context
                .buildConstraintViolationWithTemplate(
                        String.format("%s 는 허용하는 값이 아닙니다. 허용하는 값은 %s 입니다.", value, String.join(", ", allows)))
                .addConstraintViolation();

        return false;
    }
}
