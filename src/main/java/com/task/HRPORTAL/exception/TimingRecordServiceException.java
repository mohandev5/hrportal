package com.task.HRPORTAL.exception;

public class TimingRecordServiceException extends Exception{
    int statusCode;

    public TimingRecordServiceException(String message,int statusCode){
        super(message);
        this.statusCode=statusCode;
    }

    public TimingRecordServiceException(int value, String s) {
    }

    public TimingRecordServiceException(String s) {
    }

    public TimingRecordServiceException(String errorInEmployeeServiceLayer, String message, int value) {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
