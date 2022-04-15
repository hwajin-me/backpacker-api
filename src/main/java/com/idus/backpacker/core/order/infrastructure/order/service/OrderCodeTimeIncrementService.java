package com.idus.backpacker.core.order.infrastructure.order.service;

import com.idus.backpacker.core.order.domain.order.OrderCode;
import com.idus.backpacker.core.order.domain.order.OrderCodeService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCodeTimeIncrementService implements OrderCodeService {
    private static final long MAX_SIZE = 99L;
    private static final int MAX_DURATION_SECOND = 60;
    private static final String ORDER_CODE_TIME_INC_SERVICE_TEMPLATE = "ORDER_BCS_ORDER_CODE_TIME:%s";
    private static final String ORDER_CODE_TIME_DEC_SERVICE_TEMPLATE =
            "ORDER_BCS_ORDER_CODE_TIME_REVERSE:%s";
    private final RedisTemplate<String, Object> template;

    @Override
    public OrderCode generate(LocalDateTime dt) {
        final var currentTime = dt.format(DateTimeFormatter.ofPattern("uuMMddHHmm"));
        final var sequence = this.incrementAndSetExpire(currentTime);

        if (sequence > MAX_SIZE) {
            return this.generateReverse(currentTime);
        }

        return OrderCode.of(String.format("%s%02d", currentTime, sequence));
    }

    private OrderCode generateReverse(String currentTime) {
        final var sequence = this.decrementAndSetExpire(currentTime);

        return OrderCode.of(String.format("%02d%s", sequence, currentTime));
    }

    private Long incrementAndSetExpire(String time) {
        final var sequence = this.template.opsForValue().increment(time);

        if (sequence == null || sequence == 1L) {
            this.template.opsForValue().set(getKey(time), 1, Duration.ofSeconds(MAX_DURATION_SECOND));
            return 1L;
        }

        return sequence;
    }

    private Long decrementAndSetExpire(String time) {
        if (!Boolean.TRUE.equals(this.template.hasKey(getReverseKey(time)))) {
            this.template
                    .opsForValue()
                    .set(getReverseKey(time), (int) MAX_SIZE, Duration.ofSeconds(MAX_DURATION_SECOND));
            return MAX_SIZE;
        }

        final var sequence = this.template.opsForValue().decrement(getReverseKey(time));
        if (sequence == null) {
            throw new OrderCodeTimeIncrementServiceException("주문 번호 생성에 실패하였습니다.");
        }

        return sequence;
    }

    private String getKey(String time) {
        return String.format(ORDER_CODE_TIME_INC_SERVICE_TEMPLATE, time);
    }

    private String getReverseKey(String time) {
        return String.format(ORDER_CODE_TIME_DEC_SERVICE_TEMPLATE, time);
    }
}
