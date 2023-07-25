package com.task.HRPORTAL.exception;

public class SecurityExeception extends Exception{

    int statusCode;

    public SecurityExeception(String message,int statusCode){
        super(message);
        this.statusCode =statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
