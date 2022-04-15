package com.idus.backpacker.api.user.application.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.idus.backpacker.api.BackpackerApiApplicationTest;
import com.idus.backpacker.api.user.application.exception.UserNotFoundException;
import com.idus.backpacker.api.user.application.query.field.FindUserField;
import com.idus.backpacker.api.user.application.query.result.UserResult;
import com.idus.backpacker.core.kernel.domain.share.Email;
import com.idus.backpacker.core.kernel.domain.share.PhoneNumber;
import com.idus.backpacker.core.user.domain.share.OrderId;
import com.idus.backpacker.core.user.domain.user.*;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@DisplayName("User > App > Query > UserQuery 테스트")
class UserQueryTest extends BackpackerApiApplicationTest {
    @Autowired private UserQuery query;

    @Autowired private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        var factory =
                new UserFactory(
                        UserFactory.Data.builder()
                                .id(UserId.of(UUID.fromString("9d74ae87-ca26-40f7-9ef8-94611599a117")))
                                .name(UserName.of("TEST"))
                                .nickName(UserNickName.of("test"))
                                .password("pw")
                                .email(Email.of("test@gmail.com"))
                                .phoneNumber(PhoneNumber.of("01011112222"))
                                .information(UserInformation.of(UserSex.MALE))
                                .build());
        var user = factory.create();

        user.recordOrder(
                UserOrder.of(
                        OrderId.of(UUID.randomUUID()), "CODE", "NAME", LocalDateTime.of(2022, 1, 1, 0, 0, 0)));
        this.userRepository.save(user);
    }

    @AfterEach
    void cleanUp() {
        this.userRepository.deleteAll();
    }

    @Test
    @DisplayName("단일 유저정보를 가지고 올 수 있어야 한다")
    void should_be_able_to_get_user() {
        var result = query.findUserBy(UUID.fromString("9d74ae87-ca26-40f7-9ef8-94611599a117"));

        assertThat(result).isInstanceOf(UserResult.class).isNotNull();
    }

    @Test
    @DisplayName("단일 유저정보를 가지고 올 때 유저를 찾지 못했다면 에러를 발생시켜야 한다")
    void should_raise_an_exception_When_not_exists() {
        assertThatThrownBy(
                        () -> query.findUserBy(UUID.fromString("6022addf-1f22-4ae9-a53e-ba06b37684aa")))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("유저 리스트를 가지고 올 수 있어야 한다")
    void should_be_able_to_get_user_list() {
        var result = query.findBy(FindUserField.builder().build(), PageRequest.ofSize(5));

        assertThat(result.getContent()).isNotEmpty();
    }
}
