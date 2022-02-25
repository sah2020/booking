package uz.exadel.hotdeskbooking.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkplaceCustomException extends RuntimeException {
    private int lineNumber;
    private String field;
    private String message;
    private int status;

    public WorkplaceCustomException(String message, int lineNumber, String field, int status) {
        super(message);
        this.message = message;
        this.lineNumber = lineNumber;
        this.field = field;
        this.status = status;
    }
}
