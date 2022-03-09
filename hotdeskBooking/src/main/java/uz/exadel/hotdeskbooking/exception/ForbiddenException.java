package uz.exadel.hotdeskbooking.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends HotdeskBookingGlobalException{
    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.value());
    }
}
