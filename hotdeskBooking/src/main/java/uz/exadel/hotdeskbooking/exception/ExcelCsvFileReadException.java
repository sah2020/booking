package uz.exadel.hotdeskbooking.exception;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
@Setter
public class ExcelCsvFileReadException extends RuntimeException{
    private int code;
    private String status;
    private Object error;

    {
        this.code = HttpStatus.BAD_REQUEST.value();
        this.status = HttpStatus.BAD_REQUEST.getReasonPhrase();
    }

    public ExcelCsvFileReadException(Object error) {
        this.error = error;
    }
}
