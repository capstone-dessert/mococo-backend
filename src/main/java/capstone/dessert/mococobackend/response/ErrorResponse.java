package capstone.dessert.mococobackend.response;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

public record ErrorResponse(String code, String message, Map<String, String> validation) {

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}
