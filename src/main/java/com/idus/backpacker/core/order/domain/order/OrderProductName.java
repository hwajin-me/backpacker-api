package com.idus.backpacker.core.order.domain.order;

import com.idus.backpacker.core.kernel.domain.share.ValueObject;
import lombok.*;
import org.springframework.util.StringUtils;

@ValueObject
@EqualsAndHashCode
public class OrderProductName {
    @NonNull private String value;

    private OrderProductName(String value) {
        this.validate(value);

        this.value = value;
    }

    public static OrderProductName of(String value) {
        return new OrderProductName(value);
    }

    private void validate(String value) {
        if (!StringUtils.hasLength(value)) {
            throw new OrderDomainInvalidInputException("빈 문자는 허용하지 않습니다.", "ORDER_PRODUCT_NAME");
        }
    }

    @Override
    public String toString() {
        return this.value;
    }
}
