package ua.malibu.ostpc.dtos;

import ua.malibu.ostpc.exceptions.rest.RestException;

public class RestExceptionDto implements IDto<RestException> {

    private String message;

    private Integer code;

    private Integer status;

    @Override
    public RestExceptionDto convert(RestException object) {
        this.setMessage(object.getMessage());
        this.setCode(object.getCode());
        this.setStatus(object.getStatus().value());
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
