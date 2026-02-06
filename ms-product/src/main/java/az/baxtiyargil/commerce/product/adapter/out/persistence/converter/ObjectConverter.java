package az.baxtiyargil.commerce.product.adapter.out.persistence.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public final class ObjectConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> byte[] toByte(T obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not serialize `object` to `byte`", e);
        }
    }

    public static <T> T toObject(byte[] bytes, Class<T> clazz) {
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not deserialize `object` from `byte`", e);
        }
    }

    private ObjectConverter() {}
}
