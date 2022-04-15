package com.idus.backpacker.core.user.infrastructure.user.converter;

import com.idus.backpacker.core.user.domain.user.UserNickName;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserNickNameConverter implements AttributeConverter<UserNickName, String> {
    @Override
    public String convertToDatabaseColumn(UserNickName attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.toString();
    }

    @Override
    public UserNickName convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return UserNickName.of(dbData);
    }
}
