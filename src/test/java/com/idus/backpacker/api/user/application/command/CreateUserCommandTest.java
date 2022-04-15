package com.idus.backpacker.api.user.application.command;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.idus.backpacker.api.BackpackerApiApplicationTest;
import com.idus.backpacker.api.user.application.exception.UserAlreadyExistsException;
import com.idus.backpacker.core.user.domain.user.UserRepository;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("User > Application > Command > CreateUserCommand 테스트")
class CreateUserCommandTest extends BackpackerApiApplicationTest {
    @Autowired private CreateUserCommand command;

    @Autowired private UserRepository userRepository;

    @AfterEach
    void cleanUp() {
        this.userRepository.deleteAll();
    }

    @Test
    @DisplayName("이메일로 이미 가입되어있는 회원이 없다면 가입에 성공해야 한다")
    void should_be_able_to_signup() {
        assertAll(() -> command.invoke(this.makeModel()));
    }

    @Test
    @DisplayName("이미 가입되어 있는 회원이 있다면 에러가 발생해야 한다")
    void should_be_raise_an_exception_When_already_exists() {
        command.invoke(this.makeModel());
        assertThatThrownBy(() -> command.invoke(this.makeModel()))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    private CreateUserCommand.Model makeModel() {
        return CreateUserCommand.Model.builder()
                .id(UUID.randomUUID())
                .name("NAME")
                .nickName("nickname")
                .password("test1234")
                .sex("MALE")
                .email("test@gmail.com")
                .phoneNumber("01022223333")
                .build();
    }
}
