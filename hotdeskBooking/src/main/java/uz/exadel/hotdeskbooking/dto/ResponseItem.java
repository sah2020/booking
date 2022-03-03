package uz.exadel.hotdeskbooking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseItem implements Serializable {
    private String message;
    private int statusCode = HttpStatus.OK.value();
    private Object data;
    private boolean success = true;
    public ResponseItem(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ResponseItem(String message) {
        this.message = message;
    }

    public ResponseItem(Object data) {
        this.data = data;
    }

    public ResponseItem(String message, Object data, int statusCode) {
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
    }

    public ResponseItem(String message, boolean success, int statusCode) {
        this.message = message;
        this.success = success;
        this.statusCode = statusCode;
    }

    public ResponseItem(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ResponseItem(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
