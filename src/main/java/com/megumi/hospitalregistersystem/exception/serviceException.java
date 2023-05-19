package com.megumi.hospitalregistersystem.exception;

public class serviceException extends RuntimeException{
    private String code;
    public String getCode() {
        return code;
    }
    public serviceException(String message) {
        super(message);
    }
    public serviceException(String code,String message) {
        super(message);
        this.code = code;
    }
}
