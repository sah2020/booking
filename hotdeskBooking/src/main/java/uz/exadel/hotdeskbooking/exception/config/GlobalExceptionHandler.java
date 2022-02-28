package uz.exadel.hotdeskbooking.exception.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.exception.*;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler{

    private final MessageSource messageSource;

    @ExceptionHandler(value = {RestException.class})
    public ResponseEntity<ResponseItem> handleException(RestException ex) {
        return ResponseEntity.status(ex.getStatus()).body(
                new ResponseItem(
                        ex.getMessage(),
                        false,
                        ex.getStatus()
                )
        );
    }

    @ExceptionHandler(value = {Exception.class})
    public HttpEntity<ResponseItem> handleException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseItem(
                        "Server error",
                        500
                )
        );
    }


    @ExceptionHandler(OfficeCustomException.class)
    public ResponseEntity<ResponseItem> handleOfficeException(OfficeCustomException exception){
        ResponseItem apiExceptionResponse
                = new ResponseItem(exception.getMessage(), 409);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiExceptionResponse);
    }

    @ExceptionHandler(MapCustomException.class)
    public ResponseEntity<ResponseItem> handleMapException(MapCustomException exception) {
        ResponseItem apiExceptionResponse = new ResponseItem(exception.getMessage(), 409);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiExceptionResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, Locale locale){
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                        ex.getCode(),
                        messageSource.getMessage(ex.getMessage(), null, locale)));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, Locale locale){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        GlobalErrorCode.ERROR_ENTITY_NOT_FOUND,
                        messageSource.getMessage(ex.getMessage(), null, locale)));
    }

    @ExceptionHandler(ExcelCsvFileReadException.class)
    public ResponseEntity<Object> handleExcelCsvFileReadException(ExcelCsvFileReadException ex, Locale locale){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        GlobalErrorCode.BAD_REQUEST,
                        messageSource.getMessage(ex.getMessage(), null, locale),
                        ex.getError()
                        )
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(ExcelCsvFileReadException ex, Locale locale){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                                GlobalErrorCode.BAD_REQUEST,
                                messageSource.getMessage(ex.getMessage(), null, locale),
                                ex.getError()
                        )
                );
    }


}
