package uz.exadel.hotdeskbooking.exception;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotdeskBookingGlobalException extends RuntimeException{
    private int code;
    private String status;

    public HotdeskBookingGlobalException (String message, String status, int code) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
