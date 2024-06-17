package capstone.dessert.mococobackend.exception;

import org.springframework.http.HttpStatus;

public class RecommendationAPIException extends ApplicationException {

    public static final String MESSAGE = "추천 서버 오류";

    public RecommendationAPIException() {
        super(MESSAGE);
    }

    public RecommendationAPIException(String message) {
        super(MESSAGE + ": " + message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
