package com.task.HRPORTAL.exception;

public class EmployeeServiceException extends RuntimeException{

    int statusCode;

    public EmployeeServiceException(String message,int statusCode){
        super(message);
        this.statusCode=statusCode;
    }

    public EmployeeServiceException(int value, String s) {
    }

    public EmployeeServiceException(String s) {
    }

    public EmployeeServiceException(String errorInEmployeeServiceLayer, String message, int value) {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
