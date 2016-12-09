package org.tw.spike.hystrix.exception;

public class ServiceUnavailableException extends RuntimeException{

    private String errorMessage;

    public ServiceUnavailableException(String message){
        super(message);
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
