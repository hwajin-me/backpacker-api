package com.idus.backpacker.core.kernel.domain.share;

import com.idus.backpacker.util.phonenumber.PhoneNumberHelper;
import com.idus.backpacker.util.phonenumber.PhoneNumberHelperException;
import lombok.EqualsAndHashCode;

/** 휴대폰 번호 Value Object - 국제 표준은 15자이며, 더 길 수 없습니다. */
@ValueObject
@EqualsAndHashCode
public class PhoneNumber {
    private final String value;

    public static PhoneNumber of(String value) {
        return new PhoneNumber(value);
    }

    private PhoneNumber(String value) {
        try {
            this.value = PhoneNumberHelper.format(value);
        } catch (PhoneNumberHelperException exception) {
            throw new DomainInvalidInputException(
                    String.format("%s 는 잘못된 휴대폰 번호입니다.", value), "PHONE_NUMBER", exception);
        }
    }

    @Override
    public String toString() {
        return this.value;
    }
}
