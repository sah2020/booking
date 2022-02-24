package uz.exadel.hotdeskbooking.exception;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VacationCustomException {
    @ExceptionHandler(value = ChangeSetPersister.NotFoundException.class)
    public HttpEntity<?> handleVacationException(ChangeSetPersister.NotFoundException exception){
        return null;
    }
}
