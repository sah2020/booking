package uz.exadel.hotdeskbooking.exception.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import uz.exadel.hotdeskbooking.dto.response.ResponseItem;
import uz.exadel.hotdeskbooking.exception.ExcelCsvFileReadException;
import uz.exadel.hotdeskbooking.exception.HotdeskBookingGlobalException;
import uz.exadel.hotdeskbooking.exception.RestException;
import uz.exadel.hotdeskbooking.response.error.ErrorResponse;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(HotdeskBookingGlobalException.class)
    public ResponseEntity<Object> handleHotdeskBookingGlobalException(HotdeskBookingGlobalException ex) {
        return ResponseEntity
                .status(ex.getCode())
                .body(new ErrorResponse(
                                ex.getCode(),
                                ex.getStatus(),
                                messageSource.getMessage(ex.getMessage(), null, Locale.ENGLISH)
                        )
                );
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxUploadSizeExceededException() {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                messageSource.getMessage("api.error.workplace.fileSizeExceed", null, Locale.ENGLISH)
                        )
                );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException() {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                messageSource.getMessage("api.error.workplace.notMatchedDataTypeInColumn", null, Locale.ENGLISH)
                        )
                );
    }

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
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseItem("Server error", 500));
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public HttpEntity<ResponseItem> handleAuthenticationException(AuthenticationException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseItem(messageSource.getMessage("api.error.unauthorized", null, Locale.ENGLISH), 401));
    }


    @ExceptionHandler(value = {AccessDeniedException.class})
    public HttpEntity<ResponseItem> handleAccessDeniedException(AccessDeniedException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseItem(messageSource.getMessage("api.error.forbidden", null, Locale.ENGLISH), 403));
    }

    @ExceptionHandler(ExcelCsvFileReadException.class)
    public ResponseEntity<Object> handleExcelCsvFileReadException(ExcelCsvFileReadException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getCode(), ex.getStatus(), ex.getError()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(ExcelCsvFileReadException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        ex.getCode(),
                        ex.getStatus(),
                        ex.getError())
                );
    }
}
