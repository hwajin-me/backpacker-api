package com.idus.backpacker.api.user.application.query.field;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindUserField {
    private final String name;
    private final String email;
}
