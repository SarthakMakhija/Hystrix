package org.tw.spike.hystrix.model;

public class ErrorMetaData {

    private String message;

    public ErrorMetaData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
