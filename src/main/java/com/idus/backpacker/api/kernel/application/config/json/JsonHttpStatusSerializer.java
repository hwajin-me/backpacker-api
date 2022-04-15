package com.idus.backpacker.api.kernel.application.config.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import org.springframework.http.HttpStatus;

public class JsonHttpStatusSerializer extends StdSerializer<HttpStatus> {
    public JsonHttpStatusSerializer() {
        super(HttpStatus.class);
    }

    @Override
    public void serialize(HttpStatus value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        gen.writeNumber(value.value());
    }
}
