package com.idus.backpacker.core.order.infrastructure.order.converter;

import com.idus.backpacker.core.order.domain.order.OrderCode;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderCodeConverter implements AttributeConverter<OrderCode, String> {
    @Override
    public String convertToDatabaseColumn(OrderCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.toString();
    }

    @Override
    public OrderCode convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return OrderCode.of(dbData);
    }
}
