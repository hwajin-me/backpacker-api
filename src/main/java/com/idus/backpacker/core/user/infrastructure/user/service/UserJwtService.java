package com.idus.backpacker.core.user.infrastructure.user.service;

import com.idus.backpacker.core.user.domain.user.UserId;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserJwtService {
    private static final String REVOKE_KEY_TEMPLATE = "USER_JWT_REVOKE:%s";

    private final RedisTemplate<String, String> redisTemplate;
    private final UserJwtServiceProperties properties;

    public UserJwt generate(UserId id, UserDetails details) {
        final var tokenId = UUID.randomUUID();

        return UserJwt.builder()
                .accessToken(this.generateAccessToken(tokenId, id, details))
                .refreshToken(this.generateRefreshToken(id, tokenId))
                .expiresIn(this.properties.getExpiresIn())
                .refreshExpiresIn(this.properties.getRefreshExpiresIn())
                .build();
    }

    /**
     * 발급한 토큰을 파기한다. 발급한 토큰은 고유 아이디가 있고, 이를 Refresh Token과 공유한다. 해당 값을 통하여 한 토큰만 파기하여도 양 토큰 모두 파기할 수
     * 있도록 한다.
     *
     * @param token Access or Refresh token
     */
    public void revoke(@NonNull String token) {
        final var payload = this.parse(token);

        if (this.redisTemplate.opsForValue().get(String.format(REVOKE_KEY_TEMPLATE, payload.getId()))
                != null) {
            throw new UserJwtServiceException("이미 파기된 토큰입니다.");
        }

        this.redisTemplate
                .opsForValue()
                .set(
                        String.format(REVOKE_KEY_TEMPLATE, payload.getId()),
                        token,
                        Duration.ofSeconds(
                                Math.max(this.properties.getExpiresIn(), this.properties.getRefreshExpiresIn())));
    }

    public void validateAccessToken(@NonNull String token) {
        try {
            final var claims = this.parse(token);

            final var revokeType =
                    this.redisTemplate.opsForValue().get(String.format(REVOKE_KEY_TEMPLATE, claims.getId()));

            if (revokeType != null) {
                throw new UserJwtServiceException("해당 토큰은 이미 파기되었습니다.");
            }
        } catch (JwtException exception) {
            log.error("JWT Validation Exception");
            throw new UserJwtServiceException(exception.getMessage(), exception);
        }
    }

    private String generateAccessToken(
            @NonNull UUID tokenId, @NonNull UserId id, @NonNull UserDetails details) {
        final var now = new Date();
        final var after =
                new Date(now.getTime() + this.secondToMillisecond(this.properties.getExpiresIn()));

        final var claims = Jwts.claims();
        claims.put("type", "ACCESS");

        return Jwts.builder()
                .setClaims(claims)
                .setId(tokenId.toString())
                .setSubject(id.toUuid().toString())
                .setIssuer(this.properties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(after)
                .signWith(this.properties.getAlgorithm(), this.properties.getSecret())
                .compact();
    }

    private String generateRefreshToken(@NonNull UserId id, @NonNull UUID tokenId) {
        final var now = new Date();

        final var claims = Jwts.claims();
        claims.put("type", "REFRESH");

        return Jwts.builder()
                .setClaims(claims)
                .setId(tokenId.toString())
                .setSubject(id.toUuid().toString())
                .setIssuedAt(now)
                .setExpiration(
                        new Date(
                                now.getTime() + this.secondToMillisecond(this.properties.getRefreshExpiresIn())))
                .signWith(this.properties.getAlgorithm(), this.properties.getSecret())
                .compact();
    }

    public Claims parse(String token) {
        return Jwts.parser().setSigningKey(this.properties.getSecret()).parseClaimsJws(token).getBody();
    }

    private int secondToMillisecond(int second) {
        return second * 1000;
    }
}
