package capstone.dessert.mococobackend.exception;

import org.springframework.http.HttpStatus;

public class ClothingNotFoundException extends ApplicationException {

    private static final String MESSAGE = "존재하지 않는 의류입니다.";

    public ClothingNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
