package com.idus.backpacker.core.user.infrastructure.share.converter;

import com.idus.backpacker.core.user.domain.share.OrderId;
import java.nio.ByteBuffer;
import java.util.UUID;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderIdConverter implements AttributeConverter<OrderId, byte[]> {
    @Override
    public byte[] convertToDatabaseColumn(OrderId attribute) {
        if (attribute == null) {
            return null;
        }

        byte[] buffer = new byte[16];
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        byteBuffer.putLong(attribute.toUuid().getMostSignificantBits());
        byteBuffer.putLong(attribute.toUuid().getLeastSignificantBits());
        return buffer;
    }

    @Override
    public OrderId convertToEntityAttribute(byte[] dbData) {
        if (dbData == null) {
            return null;
        }

        ByteBuffer buffer = ByteBuffer.wrap(dbData);
        long high = buffer.getLong();
        long low = buffer.getLong();

        return OrderId.of(new UUID(high, low));
    }
}
