package capstone.dessert.mococobackend.exception;

import org.springframework.http.HttpStatus;

public class ScheduleNotFoundException extends ApplicationException {

    private static final String MESSAGE = "존재하지 않는 스케줄입니다.";

    public ScheduleNotFoundException(String displayName) {
        super(MESSAGE + " (스케줄: " + displayName + ")");
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
