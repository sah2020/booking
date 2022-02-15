package uz.exadel.hotdeskbooking.dto;

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
public class ResponseItem implements Serializable {
    private String message;
    private boolean success;
    private Object object;
    private HttpStatus httpStatusCode = HttpStatus.OK;

    public ResponseItem(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
