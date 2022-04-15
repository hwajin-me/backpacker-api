package com.idus.backpacker.core.order.domain.order;

import com.idus.backpacker.core.kernel.domain.share.ValueObject;
import java.util.regex.Pattern;
import lombok.*;

@ValueObject
@EqualsAndHashCode
public class OrderCode {
    private static final String PATTERN = "^[A-Z\\d]+$";
    private final String value;

    public static OrderCode of(@NonNull String value) {
        return new OrderCode(value);
    }

    private OrderCode(String value) {
        this.validate(value);

        this.value = value;
    }

    private void validate(String value) {
        if (value.length() != 12) {
            throw new OrderDomainLogicException("주문 번호의 길이는 12자여야 합니다.");
        }

        if (!Pattern.matches(PATTERN, value)) {
            throw new OrderDomainLogicException("주문 번호는 영문 대문자와 숫자로만 구성할 수 있습니다.");
        }
    }

    @Override
    public String toString() {
        return this.value;
    }
}
