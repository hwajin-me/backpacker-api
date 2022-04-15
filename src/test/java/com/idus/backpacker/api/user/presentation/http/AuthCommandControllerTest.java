package com.idus.backpacker.api.user.presentation.http;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.idus.backpacker.api.BackpackerApiApplicationTest;
import com.idus.backpacker.core.kernel.domain.share.Email;
import com.idus.backpacker.core.kernel.domain.share.PhoneNumber;
import com.idus.backpacker.core.user.domain.user.*;
import com.idus.backpacker.core.user.infrastructure.user.service.UserJwt;
import com.idus.backpacker.core.user.infrastructure.user.service.UserJwtService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayName("User > Presentation > Http > AuthCommandController 테스트")
class AuthCommandControllerTest extends BackpackerApiApplicationTest {
    @Autowired private UserJwtService service;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder encoder;
    private User user;

    private UserJwt token;

    @BeforeEach
    void setUp() {
        var factory =
                new UserFactory(
                        UserFactory.Data.builder()
                                .id(UserId.of(UUID.fromString("9d74ae87-ca26-40f7-9ef8-94611599a117")))
                                .name(UserName.of("TEST"))
                                .nickName(UserNickName.of("test"))
                                .password(encoder.encode("Test!@340MMMM"))
                                .email(Email.of("test@gmail.com"))
                                .phoneNumber(PhoneNumber.of("01011112222"))
                                .information(UserInformation.of(UserSex.MALE))
                                .build());
        user = factory.create();
        userRepository.save(user);

        token =
                this.service.generate(
                        UserId.of(UUID.fromString("9d74ae87-ca26-40f7-9ef8-94611599a117")), user);
    }

    @Test
    @DisplayName("인증이 되어 있지 않은 경우 로그아웃을 할 수 없어야 한다")
    void should_be_ret_401_When_logout_with_guest() throws Exception {

        mockMvc.perform(delete("/auth")).andExpect(status().isUnauthorized());
    }
}
