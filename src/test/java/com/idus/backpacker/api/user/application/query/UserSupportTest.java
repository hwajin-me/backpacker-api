package com.idus.backpacker.api.user.application.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.idus.backpacker.api.BackpackerApiApplicationTest;
import com.idus.backpacker.api.user.application.query.field.FindUserField;
import com.idus.backpacker.api.user.application.query.result.UsersResult;
import com.idus.backpacker.core.kernel.domain.share.Email;
import com.idus.backpacker.core.kernel.domain.share.PhoneNumber;
import com.idus.backpacker.core.user.domain.share.OrderId;
import com.idus.backpacker.core.user.domain.user.*;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@DisplayName("User > App > Query > UserSupport 테스트")
class UserSupportTest extends BackpackerApiApplicationTest {
    @Autowired private UserSupport support;

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

    @Test
    @DisplayName("이름으로 유저 정보를 필터링 하여 결과가 없을 때 빈 집합을 반환해야 한다")
    void should_be_able_to_return_empty_collection_When_empty_result() {
        var result = support.findBy(FindUserField.builder().name("없단다").build(), PageRequest.ofSize(1));

        assertThat(result.getContent()).isEmpty();
    }

    @Test
    @DisplayName("이름으로 유저 정보를 필터링 하여 결과가 있을 때 해당 원소를 반환해야 한다")
    void should_be_able_to_return_non_empty_collection_When_result_match_filtered_by_name() {
        var result = support.findBy(FindUserField.builder().name("T").build(), PageRequest.ofSize(1));

        assertThat(result.getContent())
                .isNotEmpty()
                .first()
                .isInstanceOf(UsersResult.class)
                .isNotNull();
    }

    @Test
    @DisplayName("이메일로 유저 정보를 필터링하여 결과가 없을 때 빈 집합을 반환해야 한다")
    void should_be_able_to_return_empty_collection_When_result() {
        var result =
                support.findBy(
                        FindUserField.builder().email("nononon@gmail.com").build(), PageRequest.ofSize(1));

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("이메일로 유저 정보를 필터링 하여 결과가 있을 때 해당 원소를 반환해야 한다")
    void should_be_able_to_return_non_empty_collection_When_result_match_filtered_by_email() {
        var result =
                support.findBy(
                        FindUserField.builder().email("test@gmail.com").build(), PageRequest.ofSize(1));

        assertThat(result.getContent())
                .isNotEmpty()
                .first()
                .isInstanceOf(UsersResult.class)
                .isNotNull();
    }
}
