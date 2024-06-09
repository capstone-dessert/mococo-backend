package capstone.dessert.mococobackend.exception;

import org.springframework.http.HttpStatus;

public class KakaoGeoAPIException extends ApplicationException {

    private static final String MESSAGE = "위도, 경도를 행정구역으로 변환하는데 실패했습니다.";

    public KakaoGeoAPIException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
