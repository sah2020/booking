package uz.exadel.hotdeskbooking.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.exadel.hotdeskbooking.dto.ResponseItem;

public abstract class BaseApi {
    private <T> ResponseEntity<ResponseItem<T>> send(T data, String message, String status, HttpStatus httpStatusCode) {
        return new ResponseEntity<>(new ResponseItem<>(data, message, status, httpStatusCode.value()), getHttpHeaders(), HttpStatus.OK);
    }

    protected <T> ResponseEntity<ResponseItem<T>> success(T data, String message) {
        return this.send(data, message, Status.SUCCESS.name(), HttpStatus.OK);
    }

    protected <T> ResponseEntity<ResponseItem<T>> success(T data) {
        return this.send(data, null, Status.SUCCESS.name(), HttpStatus.OK);
    }

    protected <T> ResponseEntity<ResponseItem<T>> forbidden(T data, String message) {
        return this.send(data, message, Status.FAIL.name(), HttpStatus.FORBIDDEN);
    }

    protected <T> ResponseEntity<ResponseItem<T>> created(T data, String message) {
        return this.send(data, message, Status.SUCCESS.name(), HttpStatus.CREATED);
    }

    protected <T> ResponseEntity<ResponseItem<T>> notFound(T data, String message) {
        return this.send(data, message, Status.FAIL.name(), HttpStatus.NOT_FOUND);
    }

    protected <T> ResponseEntity<ResponseItem<T>> conflict(T data, String message) {
        return this.send(data, message, Status.FAIL.name(), HttpStatus.CONFLICT);
    }

    protected <T> ResponseEntity<ResponseItem<T>> badRequest(T data, String message) {
        return this.send(data, message, Status.FAIL.name(), HttpStatus.BAD_REQUEST);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders h = new HttpHeaders();
        h.add("Content-Type", "application/json; charset=utf-8");
        return h;
    }

    private enum Status {
        SUCCESS, FAIL
    }
}
