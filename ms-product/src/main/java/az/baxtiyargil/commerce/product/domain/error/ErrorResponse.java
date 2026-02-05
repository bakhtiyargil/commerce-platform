package az.baxtiyargil.commerce.product.domain.error;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {

    private final String id;
    private final String code;
    private final String message;
    private final Integer status;
    private final LocalDateTime timestamp;
    private final List<Property> properties;

    public ErrorResponse(String id, String code, String message, Integer status) {
        this.id = id;
        this.code = code;
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.properties = new ArrayList<>();
    }

    public void addProperty(String propertyName, String message) {
        this.properties.add(new Property(propertyName, message));
    }

    public record Property(String name, String message) {
    }
}
