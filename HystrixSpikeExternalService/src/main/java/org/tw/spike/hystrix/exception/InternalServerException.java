package org.tw.spike.hystrix.exception;

public class InternalServerException extends RuntimeException {

    private String errorMessage;

    public InternalServerException(String message){
        super(message);
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
