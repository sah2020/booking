package uz.exadel.hotdeskbooking.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException{
    private String message;

    public EntityNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
