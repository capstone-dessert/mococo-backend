package capstone.dessert.mococobackend.exception;

import org.springframework.http.HttpStatus;

public class KakaoSearchAPIException extends ApplicationException {

    public static final String MESSAGE = "행정구역 검색에 실패했습니다.";

    public KakaoSearchAPIException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
