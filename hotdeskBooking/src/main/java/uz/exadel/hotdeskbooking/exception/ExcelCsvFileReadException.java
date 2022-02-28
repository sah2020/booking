package uz.exadel.hotdeskbooking.exception;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ExcelCsvFileReadException extends RuntimeException{
    private String message;
    private Object error;

    public ExcelCsvFileReadException(String message, Object error) {
        super(message);
        this.message = message;
        this.error = error;
    }
}
