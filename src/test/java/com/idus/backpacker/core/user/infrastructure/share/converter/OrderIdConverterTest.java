package com.idus.backpacker.core.user.infrastructure.share.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.idus.backpacker.core.user.domain.share.OrderId;
import java.nio.ByteBuffer;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User > Infra > Share > Converter > OrderIdConverter 테스트")
class OrderIdConverterTest {
    private OrderIdConverter converter;
    private UUID uuid;
    private OrderId orderId;

    @BeforeEach
    void setUp() {
        converter = new OrderIdConverter();
        uuid = UUID.randomUUID();
        orderId = OrderId.of(uuid);
    }

    @Test
    @DisplayName("주문 아이디 객체를 바이너리 문자열로 변환할 수 있어야 한다")
    void should_be_able_to_parse_bin_from_OrderId() {
        var result = converter.convertToDatabaseColumn(orderId);

        assertThat(result).isEqualTo(this.uuidToBinary(this.uuid));
    }

    @Test
    @DisplayName("바이너리 문자열로부터 주문 아이디 객체를 생성할 수 있어야 한다")
    void should_be_able_to_create_OrderId_from_bin() {
        var result = converter.convertToEntityAttribute(this.uuidToBinary(uuid));

        assertThat(result).isEqualTo(orderId);
    }

    private byte[] uuidToBinary(UUID value) {
        byte[] buffer = new byte[16];

        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        byteBuffer.putLong(value.getMostSignificantBits());
        byteBuffer.putLong(value.getLeastSignificantBits());
        return buffer;
    }
}
