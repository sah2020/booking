package uz.exadel.hotdeskbooking.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends HotdeskBookingGlobalException{
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value());
    }
}
