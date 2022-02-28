package uz.exadel.hotdeskbooking.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkplaceError {
    private int lineNumber;
    private String field;
    private String message;

    public WorkplaceError(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public WorkplaceError(int lineNumber, String field) {
        this.lineNumber = lineNumber;
        this.field = field;
    }
}
