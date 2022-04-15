package com.idus.backpacker.core.order.infrastructure.order.converter;

import com.idus.backpacker.core.order.domain.order.OrderProductName;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderProductNameConverter implements AttributeConverter<OrderProductName, String> {
    @Override
    public String convertToDatabaseColumn(OrderProductName attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toString();
    }

    @Override
    public OrderProductName convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return OrderProductName.of(dbData);
    }
}
