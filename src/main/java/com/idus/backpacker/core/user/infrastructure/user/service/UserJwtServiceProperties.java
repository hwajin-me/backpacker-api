package com.idus.backpacker.core.user.infrastructure.user.service;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Getter
@ConstructorBinding
@ConfigurationProperties("user.jwt")
@AllArgsConstructor
@Validated
public class UserJwtServiceProperties {
    /** JWT Secret */
    private String secret;

    /** User Token Issuer (Required) */
    private String issuer;

    /** Access token expires in (second, default is 5 min) */
    private int expiresIn;

    /** Refresh token expires in (second, default is 7-days) */
    private int refreshExpiresIn;

    /** Sign Algorithm (default is HS512) */
    private SignatureAlgorithm algorithm;
}
