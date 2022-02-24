package uz.exadel.hotdeskbooking.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkplaceError {
    private int lineNumber;
    private String field;
    private String message;
}
