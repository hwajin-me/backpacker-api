package com.idus.backpacker.core.user.domain.user;

import com.idus.backpacker.core.kernel.domain.share.ValueObject;
import java.util.regex.Pattern;
import lombok.*;
import org.springframework.util.StringUtils;

@ValueObject
@EqualsAndHashCode
public class UserNickName {
    private static final String PATTERN = "^[a-z]{1,30}$";

    private final String value;

    public static UserNickName of(String value) {
        return new UserNickName(value);
    }

    private UserNickName(String value) {
        this.validate(value);

        this.value = value;
    }

    private void validate(String value) {
        if (!StringUtils.hasText(value)) {
            throw new UserDomainInvalidInputException("빈 값을 입력할 수 없습니다.", "USER_NICK_NAME");
        }

        if (!Pattern.matches(PATTERN, value)) {
            throw new UserDomainInvalidInputException(
                    String.format("%s 는 올바르지 않은 별명입니다. 영문 소문자로 1~30자리만 가능합니다.", value), "USER_NICK_NAME");
        }
    }

    @Override
    public String toString() {
        return this.value;
    }
}
