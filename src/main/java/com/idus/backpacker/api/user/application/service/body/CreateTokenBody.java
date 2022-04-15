package com.idus.backpacker.api.user.application.service.body;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class CreateTokenBody {
    @NonNull private String accessToken;

    @NonNull private String refreshToken;
    private int expiresIn;
    private int refreshExpiresIn;
}
