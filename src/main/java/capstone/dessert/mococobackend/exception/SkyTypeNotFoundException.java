package capstone.dessert.mococobackend.exception;

import org.springframework.http.HttpStatus;

public class SkyTypeNotFoundException extends ApplicationException {

    public static final String MESSAGE = "하늘상태를 찾을 수 없습니다. ";

    public SkyTypeNotFoundException(String message) {
        super(MESSAGE + message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
