package capstone.dessert.mococobackend.exception;

import org.springframework.http.HttpStatus;

public class OutfitNotFoundException extends ApplicationException {

    public static final String MESSAGE = "존재하지 않는 코디입니다.";

    public OutfitNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NO_CONTENT.value();
    }
}
