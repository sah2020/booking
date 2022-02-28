package uz.exadel.hotdeskbooking.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{
    private String message;
    private int code;

    public BadRequestException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
