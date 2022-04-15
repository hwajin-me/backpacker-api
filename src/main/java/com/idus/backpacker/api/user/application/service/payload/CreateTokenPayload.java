package com.idus.backpacker.api.user.application.service.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class CreateTokenPayload {
    @NonNull private final String email;

    @NonNull private final String password;
}
