package com.idus.backpacker.api.user.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.idus.backpacker.api.BackpackerApiApplicationTest;
import com.idus.backpacker.api.user.application.exception.UserNotFoundException;
import com.idus.backpacker.api.user.application.service.body.CreateTokenBody;
import com.idus.backpacker.api.user.application.service.payload.CreateTokenByRefreshTokenPayload;
import com.idus.backpacker.api.user.application.service.payload.CreateTokenPayload;
import com.idus.backpacker.core.kernel.domain.share.Email;
import com.idus.backpacker.core.kernel.domain.share.PhoneNumber;
import com.idus.backpacker.core.user.domain.user.*;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayName("User > App > Service > UserTokenService 테스트")
class UserTokenServiceTest extends BackpackerApiApplicationTest {
    @Autowired private UserTokenService service;

    @Autowired private PasswordEncoder encoder;

    @Autowired private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        var factory =
                new UserFactory(
                        UserFactory.Data.builder()
                                .id(UserId.of(UUID.fromString("fabf9664-1a8d-436c-8d32-7738d1787814")))
                                .name(UserName.of("TEST"))
                                .nickName(UserNickName.of("test"))
                                .password(encoder.encode("password"))
                                .email(Email.of("test@gmail.com"))
                                .phoneNumber(PhoneNumber.of("01011112222"))
                                .build());
        var user = factory.create();

        this.userRepository.save(user);
    }

    @Test
    @DisplayName("존재하는 회원에 대해 인증 토큰을 생성할 수 있어야 한다")
    void should_be_able_to_create_token_by_user() {
        var result =
                this.service.createToken(
                        CreateTokenPayload.builder().email("test@gmail.com").password("password").build());

        assertThat(result).isInstanceOf(CreateTokenBody.class).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 회원으로 인증 토큰을 생성할 수 없어야 한다")
    void should_be_raise_an_exception_When_user_not_found() {
        assertThatThrownBy(
                        () ->
                                this.service.createToken(
                                        CreateTokenPayload.builder()
                                                .email("testtest@gmail.com")
                                                .password("password")
                                                .build()))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("비밀번호가 틀렸을 때 인증 토큰을 생성할 수 없어야 한다")
    void should_be_raise_an_exception_When_password_incorrect() {

        assertThatThrownBy(
                        () ->
                                this.service.createToken(
                                        CreateTokenPayload.builder().email("test@gmail.com").password("diff").build()))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("재인증 토큰을 통해 다시 토큰을 발급받을 수 있어야 한다")
    void should_be_able_to_create_token_by_refreshToken() {
        var foo =
                this.service.createToken(
                        CreateTokenPayload.builder().email("test@gmail.com").password("password").build());

        var bar =
                this.service.createTokenByRefreshToken(
                        CreateTokenByRefreshTokenPayload.builder().refreshToken(foo.getRefreshToken()).build());

        assertThat(bar).isInstanceOf(CreateTokenBody.class).isNotNull();
        assertThat(foo.getAccessToken()).isNotEqualTo(bar.getAccessToken());
    }

    @Test
    @DisplayName("만들어진 토큰을 파기할 수 있어야 한다")
    void should_be_able_to_revoke_token() {
        assertAll(
                () ->
                        this.service.revokeToken(
                                this.service
                                        .createToken(
                                                CreateTokenPayload.builder()
                                                        .email("test@gmail.com")
                                                        .password("password")
                                                        .build())
                                        .getAccessToken()));
    }
}
