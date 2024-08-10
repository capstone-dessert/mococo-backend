package capstone.dessert.mococobackend.exception;

import org.springframework.http.HttpStatus;

public class StyleNotFoundException extends ApplicationException {

    private static final String MESSAGE = "존재하지 않는 스타일입니다.";

    public StyleNotFoundException(String displayName) {
        super(MESSAGE + " (" + displayName + ")");
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
