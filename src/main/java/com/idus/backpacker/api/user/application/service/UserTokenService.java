package com.idus.backpacker.api.user.application.service;

import com.idus.backpacker.api.user.application.exception.UserNotFoundException;
import com.idus.backpacker.api.user.application.service.body.CreateTokenBody;
import com.idus.backpacker.api.user.application.service.payload.CreateTokenByRefreshTokenPayload;
import com.idus.backpacker.api.user.application.service.payload.CreateTokenPayload;
import com.idus.backpacker.core.kernel.domain.share.Email;
import com.idus.backpacker.core.user.domain.user.User;
import com.idus.backpacker.core.user.domain.user.UserId;
import com.idus.backpacker.core.user.domain.user.UserRepository;
import com.idus.backpacker.core.user.infrastructure.user.service.UserJwtService;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserTokenService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserJwtService jwtService;

    @NonNull
    @Transactional
    public CreateTokenBody createToken(@NonNull CreateTokenPayload payload) {
        final var user = this.getUserBy(Email.of(payload.getEmail()));

        this.validPassword(payload.getPassword(), user.getPassword());

        final var token = this.jwtService.generate(user.getId(), user);

        return CreateTokenBody.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .expiresIn(token.getExpiresIn())
                .refreshExpiresIn(token.getRefreshExpiresIn())
                .build();
    }

    @NonNull
    @Transactional
    public CreateTokenBody createTokenByRefreshToken(
            @NonNull CreateTokenByRefreshTokenPayload payload) {
        final var claims = this.jwtService.parse(payload.getRefreshToken());
        final var user =
                this.repository
                        .findById(UserId.of(UUID.fromString(claims.getSubject())))
                        .orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));

        this.jwtService.revoke(payload.getRefreshToken());
        final var token = this.jwtService.generate(user.getId(), user);

        return CreateTokenBody.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .expiresIn(token.getExpiresIn())
                .refreshExpiresIn(token.getRefreshExpiresIn())
                .build();
    }

    /**
     * 로그아웃. 토큰의 아이디를 통해 파기를 진행하기 때문에, 어떤 토큰이든 무관하게 받는다. Refresh, Access 둘 중 어떤 토큰이든 파기를 진행한다면 사용할 수
     * 없도록 된다.
     *
     * <p>이러한 이유는 둘 중 어떤 토큰이든 발급 주체, 대상은 동일하며 이에 따라 한 쪽이 의사와 무관하게 파기되었다면, 자의에 의한 행위라면 둘 다 로그아웃을 진행하는
     * 것이 맞으며 타의에 의한 로그아웃인 경우 이 또한 유출 사고로 간주할 수 있기에, 어떤 토큰이든 파기가 진행되었다면 다른 토큰도 사용할 수 없다.
     *
     * @param token Access or Refresh token
     */
    @Transactional
    public void revokeToken(@NonNull String token) {
        this.jwtService.revoke(token);
    }

    @NonNull
    private User getUserBy(Email email) {
        return this.repository
                .findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));
    }

    private void validPassword(String original, String target) {
        if (!this.passwordEncoder.matches(original, target)) {
            throw new UserNotFoundException("회원을 찾을 수 없습니다.");
        }
    }
}
