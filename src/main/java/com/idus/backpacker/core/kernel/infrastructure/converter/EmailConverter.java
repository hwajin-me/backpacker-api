package com.idus.backpacker.core.kernel.infrastructure.converter;

import com.idus.backpacker.core.kernel.domain.share.Email;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EmailConverter implements AttributeConverter<Email, String> {
    @Override
    public String convertToDatabaseColumn(Email attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.toString();
    }

    @Override
    public Email convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return Email.of(dbData);
    }
}
