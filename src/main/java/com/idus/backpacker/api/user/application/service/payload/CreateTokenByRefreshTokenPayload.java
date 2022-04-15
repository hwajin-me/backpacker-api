package com.idus.backpacker.api.user.application.service.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class CreateTokenByRefreshTokenPayload {
    @NonNull private final String refreshToken;
}
