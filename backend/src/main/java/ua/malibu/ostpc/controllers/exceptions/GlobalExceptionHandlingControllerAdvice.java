package ua.malibu.ostpc.controllers.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.malibu.ostpc.dtos.RestExceptionDto;
import ua.malibu.ostpc.exceptions.rest.RestException;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandlingControllerAdvice {

    protected Logger logger;

    public GlobalExceptionHandlingControllerAdvice() {
        logger = LoggerFactory.getLogger(getClass());
    }

    @ExceptionHandler({ RestException.class })
    public ResponseEntity<RestExceptionDto> restError(RestException exception) {
        logger.error("Request raised " + exception.getClass().getSimpleName());
        return new ResponseEntity<>(new RestExceptionDto().convert(exception), exception.getStatus());
    }

    @ExceptionHandler({ SQLException.class, DataAccessException.class })
    public String databaseError(Exception exception) {
        logger.error("Request raised " + exception.getClass().getSimpleName());
        return "databaseError";
    }
}
