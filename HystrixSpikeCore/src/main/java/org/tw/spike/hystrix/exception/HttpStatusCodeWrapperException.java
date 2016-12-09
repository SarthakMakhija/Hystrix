package org.tw.spike.hystrix.exception;

import org.springframework.http.HttpStatus;

public class HttpStatusCodeWrapperException extends RuntimeException {

    private final Throwable th;
    private final HttpStatus httpStatus;

    public HttpStatusCodeWrapperException(Throwable th, HttpStatus httpStatus){
        this.th = th;
        this.httpStatus = httpStatus;
    }

    public HttpStatus httpStatus(){
        return httpStatus;
    }
}
