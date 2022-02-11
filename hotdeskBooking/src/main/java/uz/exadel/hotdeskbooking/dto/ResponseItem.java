package uz.exadel.hotdeskbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseItem<T> implements Serializable {
    private T data;
    private String message;
    private String serverMessage;
    private String status;
    private int httpStatusCode;

    public ResponseItem(T data) {
        this(data, null, null, "success", 200);
    }

    public ResponseItem(T data, String message) {
        this(data, message, null, "success", 200);
    }

    public ResponseItem(T data, String message, String status) {
        this(data, message, null, status, 200);
    }

    public ResponseItem(T data, String message, String serverMessage, String status) {
        this(data, message, serverMessage, status, 200);
    }
}
