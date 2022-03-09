package uz.exadel.hotdeskbooking.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends HotdeskBookingGlobalException{
    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT.value());
    }
}
