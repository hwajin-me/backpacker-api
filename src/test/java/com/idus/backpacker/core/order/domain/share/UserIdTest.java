package com.idus.backpacker.core.order.domain.share;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Order > Domain > Share > UserId 테스트")
class UserIdTest {
    @Test
    @DisplayName("UUID를 통해 주문 바운디드 컨텍스트의 회원 아이디를 만들 수 있어야 한다")
    void should_be_able_to_create_userId() {
        assertThat(UserId.of(UUID.randomUUID())).isInstanceOf(UserId.class).isNotNull();
    }

    @Test
    @DisplayName("UserId 는 동등하게 비교되어야 한다")
    void should_be_compare_by_value() {
        var uuid = UUID.randomUUID();
        var foo = UserId.of(uuid);
        var bar = UserId.of(uuid);

        assertThat(foo).isEqualTo(bar);
    }

    @Test
    @DisplayName("UserId 는 UUID로 변환할 수 있어야 한다")
    void should_be_able_to_get_uuid_by_toUuid() {
        var uuid = UUID.randomUUID();
        var foo = UserId.of(uuid).toUuid();

        assertThat(foo).isEqualTo(uuid);
    }
}
