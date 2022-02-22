package uz.exadel.hotdeskbooking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseItem implements Serializable {
    private String message;
    private boolean success;
    private Object data;
    private HttpStatus httpStatusCode = HttpStatus.OK;

    public ResponseItem(String message, boolean success, HttpStatus httpStatusCode) {
        this.message = message;
        this.success = success;
        this.httpStatusCode = httpStatusCode;
    }

    public ResponseItem(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
