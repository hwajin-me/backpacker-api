package com.idus.backpacker.core.user.infrastructure.user.service;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserJwt {
    private String accessToken;
    private String refreshToken;
    private int expiresIn;
    private int refreshExpiresIn;
}
