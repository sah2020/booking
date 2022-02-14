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
    private String status;
    private int httpStatusCode;

    public ResponseItem(T data, String message, String status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }
}
