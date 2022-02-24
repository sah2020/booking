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
    private int statusCode = 200;
    private Object data;
    private boolean success;
    private HttpStatus httpStatusCode = HttpStatus.OK;
    private Object error;

    public ResponseItem(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ResponseItem(Object data) {
        this.data = data;
    }

    public ResponseItem(String message, boolean success, Object data, HttpStatus httpStatusCode) {
        this.message = message;
        this.success = success;
        this.data = data;
        this.httpStatusCode = httpStatusCode;
    }

    public ResponseItem(String message, boolean success, HttpStatus httpStatusCode) {
        this.message = message;
        this.success = success;
        this.httpStatusCode = httpStatusCode;
    }

    public ResponseItem(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ResponseItem(boolean success, HttpStatus httpStatusCode, Object error) {
        this.success = success;
        this.error = error;
        this.httpStatusCode = httpStatusCode;
    }
}
