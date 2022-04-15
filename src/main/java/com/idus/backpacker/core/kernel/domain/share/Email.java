package com.idus.backpacker.core.kernel.domain.share;

import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;

@ValueObject
@EqualsAndHashCode
public class Email {
    private static final String PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private final String value;

    public static Email of(String value) {
        return new Email(value);
    }

    private Email(String value) {
        this.validate(value);

        this.value = value;
    }

    private void validate(String value) {
        if (!Pattern.matches(PATTERN, value)) {
            throw new DomainInvalidInputException(String.format("%s 는 잘못된 이메일 입니다.", value), "EMAIL");
        }
    }

    @Override
    public String toString() {
        return this.value;
    }
}
