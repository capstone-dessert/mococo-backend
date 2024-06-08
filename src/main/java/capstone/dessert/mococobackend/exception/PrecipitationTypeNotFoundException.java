package capstone.dessert.mococobackend.exception;

import org.springframework.http.HttpStatus;

public class PrecipitationTypeNotFoundException extends ApplicationException {

    public static final String MESSAGE = "강수형태를 찾을 수 없습니다. ";

    public PrecipitationTypeNotFoundException(String message) {
        super(MESSAGE + message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
