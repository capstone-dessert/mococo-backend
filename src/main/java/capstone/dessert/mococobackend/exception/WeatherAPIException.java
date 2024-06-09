package capstone.dessert.mococobackend.exception;

import org.springframework.http.HttpStatus;

public class WeatherAPIException extends ApplicationException {

    private static final String MESSAGE = "날씨 정보를 가져오는데 실패했습니다.";

    public WeatherAPIException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
