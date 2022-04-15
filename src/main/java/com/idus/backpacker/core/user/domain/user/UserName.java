package com.idus.backpacker.core.user.domain.user;

import com.idus.backpacker.core.kernel.domain.share.ValueObject;
import java.util.regex.Pattern;
import lombok.*;
import org.springframework.util.StringUtils;

@ValueObject
@EqualsAndHashCode
public class UserName {
    private static final String PATTERN = "^[\uAC00-\uD7A3A-z]{1,20}$";

    private final String value;

    public static UserName of(String value) {
        return new UserName(value);
    }

    private UserName(String value) {
        this.validate(value);

        this.value = value;
    }

    private void validate(String value) {
        if (!StringUtils.hasText(value)) {
            throw new UserDomainInvalidInputException("빈 값을 입력할 수 없습니다.", "USER_NAME");
        }

        if (!Pattern.matches(PATTERN, value)) {
            throw new UserDomainInvalidInputException(
                    String.format("%s 는 올바르지 않은 이름입니다. 이름은 한글 완성형, 영문 대소문자로 최대 20자까지 입력할 수 있습니다.", value),
                    "USER_NAME");
        }
    }

    @Override
    public String toString() {
        return this.value;
    }
}
