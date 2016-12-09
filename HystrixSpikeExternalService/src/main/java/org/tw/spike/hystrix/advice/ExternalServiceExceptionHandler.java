package org.tw.spike.hystrix.advice;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tw.spike.hystrix.exception.InternalServerException;
import org.tw.spike.hystrix.exception.ServiceUnavailableException;
import org.tw.spike.hystrix.model.ErrorMetaData;

@RestControllerAdvice
public class ExternalServiceExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMetaData handleInternalServerException(InternalServerException e, HttpServletRequest req){
        return new ErrorMetaData(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorMetaData handleServiceUnavailableException(ServiceUnavailableException e, HttpServletRequest req){
        return new ErrorMetaData(e.getMessage());
    }
}