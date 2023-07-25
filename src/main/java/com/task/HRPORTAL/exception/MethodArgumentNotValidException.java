package com.task.HRPORTAL.exception;

public class MethodArgumentNotValidException extends Exception{

    int statusCode;
    public MethodArgumentNotValidException(String message,int statusCode){
        super(message);
        this.statusCode=statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
