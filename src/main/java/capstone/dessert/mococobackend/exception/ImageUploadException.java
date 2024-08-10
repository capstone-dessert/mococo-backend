package capstone.dessert.mococobackend.exception;

import org.springframework.http.HttpStatus;

public class ImageUploadException extends ApplicationException {

    private static final String MESSAGE = "이미지 업로드에 실패하였습니다.";

    public ImageUploadException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
