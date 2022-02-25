package uz.exadel.hotdeskbooking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Object error;

    public ResponseItem(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ResponseItem(Object data) {
        this.data = data;
    }

    public ResponseItem(String message, boolean success, Object data, int statusCode) {
        this.message = message;
        this.success = success;
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

    public ResponseItem(boolean success, int statusCode, Object error) {
        this.success = success;
        this.error = error;
        this.statusCode = statusCode;
    }
}
