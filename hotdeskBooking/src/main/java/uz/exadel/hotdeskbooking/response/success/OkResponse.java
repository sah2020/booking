package uz.exadel.hotdeskbooking.response.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OkResponse {
    private int code;
    private String status;
    private String message;
    private Object data;

    {
        this.code = HttpStatus.OK.value();
        this.status = HttpStatus.OK.getReasonPhrase();
    }

    public OkResponse(Object data) {
        this.data = data;
    }

    public OkResponse(String message) {
        this.message = message;
    }

    public OkResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
