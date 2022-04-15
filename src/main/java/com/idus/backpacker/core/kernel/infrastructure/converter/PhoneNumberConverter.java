package com.idus.backpacker.core.kernel.infrastructure.converter;

import com.idus.backpacker.core.kernel.domain.share.PhoneNumber;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PhoneNumberConverter implements AttributeConverter<PhoneNumber, String> {
    @Override
    public String convertToDatabaseColumn(PhoneNumber attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.toString();
    }

    @Override
    public PhoneNumber convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return PhoneNumber.of(dbData);
    }
}
