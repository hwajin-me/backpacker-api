package com.idus.backpacker.core.user.infrastructure.user.converter;

import com.idus.backpacker.core.user.domain.user.UserName;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserNameConverter implements AttributeConverter<UserName, String> {
    @Override
    public String convertToDatabaseColumn(UserName attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.toString();
    }

    @Override
    public UserName convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return UserName.of(dbData);
    }
}
