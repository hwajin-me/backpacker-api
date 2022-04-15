package com.idus.backpacker.core.user.infrastructure.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

import com.idus.backpacker.core.user.domain.user.UserId;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith({MockitoExtension.class})
class UserJwtServiceTest {
    private UserId userId;
    private UserJwtService service;
    private UserJwtServiceProperties properties;

    @Mock private RedisTemplate<String, String> redisTemplate;

    @Mock private ValueOperations<String, String> operations;

    @BeforeEach
    void setUp() {
        userId = UserId.of(UUID.randomUUID());
        properties =
                new UserJwtServiceProperties("SECRET", "ISSUER", 300, 10080, SignatureAlgorithm.HS512);
        service = new UserJwtService(redisTemplate, properties);
    }

    @Test
    @DisplayName("토큰을 파기할 수 있어야 한다")
    void should_be_able_to_revoke_token() {
        given(redisTemplate.opsForValue()).willReturn(operations);

        when(operations.get(any())).thenReturn(null);
        this.service.revoke(this.generateKey().getAccessToken());

        then(operations).should(atLeastOnce()).set(any(), any(), any());
    }

    @Test
    @DisplayName("이미 파기된 토큰이라면 에러를 발생시켜야 한다")
    void should_raise_an_exception_When_already_revoked() {
        given(redisTemplate.opsForValue()).willReturn(operations);

        when(operations.get(any())).thenReturn("YES, EXISTS!");
        assertThatThrownBy(() -> this.service.revoke(this.generateKey().getAccessToken()))
                .isInstanceOf(UserJwtServiceException.class);
    }

    @Test
    @DisplayName("UserId와 UserDetails를 통해 토큰을 만들 수 있어야 한다")
    void should_be_able_to_create_tokens_with_UserDetails() {
        var details = mock(UserDetails.class);

        var result = this.service.generate(userId, details);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("시간이 지나지 않았고 시크릿이 동일한 키라면 검증에 성공해야 한다")
    void should_be_return_true_When_non_expired_and_match_secrets() {
        when(redisTemplate.opsForValue()).thenReturn(operations);

        var key = this.generateKey();

        assertAll(() -> this.service.validateAccessToken(key.getAccessToken()));
    }

    @Test
    @DisplayName("시간이 지나지 않았고 시크릿이 동일한 Refresh token 이라면 검증에 성공해야 한다")
    void should_be_return_true_When_non_expired_and_match_secrets_RefreshToken() {
        when(redisTemplate.opsForValue()).thenReturn(operations);

        var key = this.generateKey();

        assertAll(() -> this.service.validateAccessToken(key.getRefreshToken()));
    }

    @Test
    @DisplayName("시간이 지나지 않았지만 시크릿이 다른 키라면 검증에 실패해야 한다")
    void should_raise_an_exception_When_diff_secret_key_validation() {

        var key =
                "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwiaWF0Ijo5OTE2MjM5MDIyfQ.4HgLNYdXj8r2eNB6heocA8TgOTqNoUmOptctkVwiSbm_V4PimfyGGm5WqpT_4siPT8GD7YlPdkYVlFocRl2MsA";

        assertThatThrownBy(() -> this.service.validateAccessToken(key))
                .isInstanceOf(UserJwtServiceException.class);
    }

    @Test
    @DisplayName("시간이 지난 키는 시크릿과 무관하게 검증에 실패해야 한다")
    void should_raise_an_exception_When_same_secret_but_expired() {
        var key =
                "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwiaWF0IjoxfQ.vTpaolArBUZ4fqJuSRpFYIl68ZgEz-AxgaXBxg-CqVeqO8GwCzMeXZeM90jvP6tby9E58rPHrFoCPTJtbnbZFA";

        assertThatThrownBy(() -> this.service.validateAccessToken(key))
                .isInstanceOf(UserJwtServiceException.class);
    }

    private UserJwt generateKey() {
        return this.service.generate(userId, mock(UserDetails.class));
    }
}
