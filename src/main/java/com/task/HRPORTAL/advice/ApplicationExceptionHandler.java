package com.task.HRPORTAL.advice;

import com.task.HRPORTAL.exception.AccessDeniedException;
import com.task.HRPORTAL.exception.EmployeeServiceException;
import com.task.HRPORTAL.exception.SecurityExeception;
import com.task.HRPORTAL.exception.TimingRecordServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> InvalidCredentials(MethodArgumentNotValidException exception){
        Map<String,String>error = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(X->{
            error.put(X.getField(),X.getDefaultMessage());
            error.put("statusCode", String.valueOf(exception.getStatusCode()));
        });
        return error;
    }
    @ExceptionHandler(TimingRecordServiceException.class)
    @ResponseBody
    public Map<String,String>BusinessException(TimingRecordServiceException ex){
        Map<String,String> errormap = new HashMap<>();
        errormap.put("errorMessage:", ex.getMessage());
        errormap.put("statusCode", String.valueOf(ex.getStatusCode()));
        return errormap;
    }


    @ExceptionHandler(EmployeeServiceException.class)
    @ResponseBody
    public Map<String,String>employeeException(EmployeeServiceException ex){
        Map<String,String> errormap = new HashMap<>();
        errormap.put("errorMessage:", ex.getMessage());
        errormap.put("statusCode", String.valueOf(ex.getStatusCode()));
        return errormap;
    }

    @ExceptionHandler(SecurityExeception.class)
    public Map<String,String>security(SecurityExeception ex){
        Map<String,String> errormap = new HashMap<>();
        errormap.put("errorMessage:", ex.getMessage());
        errormap.put("statusCode", String.valueOf(ex.getStatusCode()));
        return errormap;
    }

    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String,String>security(AccessDeniedException ex){
        Map<String,String> errormap = new HashMap<>();
        errormap.put("errorMessage:", ex.getMessage());
//        errormap.put("statusCode", String.valueOf(ex.getStatusCode()));
        return errormap;
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        errorMap.put("statusCode", "500"); // Internal Server Error status code
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
    }



}
