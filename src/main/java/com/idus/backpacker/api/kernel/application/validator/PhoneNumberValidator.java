package com.idus.backpacker.api.kernel.application.validator;

import com.idus.backpacker.util.phonenumber.PhoneNumberHelper;
import com.idus.backpacker.util.phonenumber.PhoneNumberHelperException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            return PhoneNumberHelper.isValid(value);
        } catch (PhoneNumberHelperException exception) {

            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(
                            String.format("휴대폰 번호가 올바르지 않습니다. [Reason=%s]", exception.getMessage()))
                    .addConstraintViolation();
            return false;
        }
    }
}
