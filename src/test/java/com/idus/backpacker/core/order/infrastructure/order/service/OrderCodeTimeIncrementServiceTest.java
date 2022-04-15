package com.idus.backpacker.core.order.infrastructure.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.idus.backpacker.core.order.domain.order.OrderCode;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@ExtendWith({MockitoExtension.class})
@DisplayName("Order > Infra > Order > Service > OrderCodeTimeIncrementService 테스트")
class OrderCodeTimeIncrementServiceTest {
    @Mock private RedisTemplate<String, Object> redis;

    @Mock private ValueOperations<String, Object> operation;

    @InjectMocks private OrderCodeTimeIncrementService service;

    private Clock clock;
    private LocalDateTime current;
    private String codePrefix = "2001010000";

    @BeforeEach
    void setUp() {
        when(redis.opsForValue()).thenReturn(operation);
        clock = Clock.fixed(Instant.parse("2020-01-01T00:00:00Z"), ZoneId.of("UTC"));
        current = LocalDateTime.now(clock);
    }

    @Nested
    @DisplayName("YYMMDDHHMM__ 증가 테스트")
    class IncrementTest {
        @Test
        @DisplayName("현재 시분에 아무 증감 값이 없는 경우 시간01 코드가 만들어져야 한다")
        void should_be_able_to_create_TIME01_When_not_exists() {
            when(operation.increment(any())).thenReturn(1L);

            var code = service.generate(current);

            var result = code.equals(OrderCode.of(codePrefix + "01"));

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("현재 시분에 증감값이 없는 경우 코드 파기 정책을 수행해야 한다")
        void should_be_able_to_set_expire_time_When_not_exists() {
            when(operation.increment(any())).thenReturn(1L);

            service.generate(current);

            verify(operation, atLeastOnce()).set(any(), any(), any());
        }
    }

    @Nested
    @DisplayName("__YYMMDDHHMM 감소 테스트")
    class DecrementTest {
        @Test
        @DisplayName("현재 시분에 시퀀스가 99를 넘었다면, 탬플릿을 교체하고 시퀀스를 감소시켜야 한다")
        void should_be_able_to_create_99TIME_When_overflow() {
            // 테스트가 객체의 숨겨진 구체적 정보를 알고 있기에, 리팩토링이 필요한 부분
            // 변화 가능성이 낮다는 점에서 이렇게 둠
            //
            when(operation.increment(any())).thenReturn(100L);

            var code = service.generate(current);

            var result = OrderCode.of("99" + codePrefix);

            assertThat(code).isEqualTo(result);
        }

        @Test
        @DisplayName("역방향 감소가 시작된 주문 번호를 생성할 수 있어야 한다")
        void should_be_able_to_create_98TIME_When_dec_1_after_overflow() {
            when(operation.increment(any())).thenReturn(101L);
            when(redis.hasKey(any())).thenReturn(true);
            when(operation.decrement(any())).thenReturn(98L);

            var code = service.generate(current);

            var result = OrderCode.of("98" + codePrefix);

            assertThat(code).isEqualTo(result);
        }

        @Test
        @DisplayName("주문 번호의 시퀀스를 최초로 감소시켰을 때 만료일시를 설정해야 한다")
        void should_be_able_to_set_expire_time_When_not_exists() {
            when(operation.increment(any())).thenReturn(100L);

            service.generate(current);

            verify(operation, atLeastOnce()).set(any(), any(), any());
        }
    }
}
