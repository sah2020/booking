package uz.exadel.hotdeskbooking.response.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatedResponse {
    private int code;
    private String status;
    private String message;
    private Object data;

    {
        this.code = HttpStatus.CREATED.value();
        this.status = HttpStatus.CREATED.getReasonPhrase();
    }

    public CreatedResponse(String message) {
        this.message = message;
    }

    public CreatedResponse(Object data) {
        this.data = data;
    }

    public CreatedResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
