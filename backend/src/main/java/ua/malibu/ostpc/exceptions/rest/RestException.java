package ua.malibu.ostpc.exceptions.rest;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {

    private HttpStatus status;

    private Integer code;

    private String message;

    public RestException(HttpStatus status, Integer code, String message) {
        this.setMessage(message);
        this.status = status;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
