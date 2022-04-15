package com.idus.backpacker.core.user.domain.user;

import java.util.Arrays;
import lombok.NonNull;

public enum UserSex {
    MALE,
    FEMALE;

    public static UserSex fromString(@NonNull String value) {
        return Arrays.stream(UserSex.values())
                .filter(item -> item.name().equals(value))
                .findAny()
                .orElseThrow(
                        () ->
                                new UserDomainLogicException(
                                        String.format("%s 는 정상적인 성별이 아닙니다. MALE 또는 FEMALE만 가능합니다.", value)));
    }
}
