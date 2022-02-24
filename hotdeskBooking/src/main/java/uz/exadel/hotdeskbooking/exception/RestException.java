package uz.exadel.hotdeskbooking.exception;

public class RestException extends RuntimeException{
    private String message;
    private int status;

    public RestException(String message, int status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
