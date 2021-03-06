package ua.nure.providence.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.nure.providence.exceptions.BaseException;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Something went wrong on the server")
public class ServerErrorBaseRestException extends BaseException {
}
