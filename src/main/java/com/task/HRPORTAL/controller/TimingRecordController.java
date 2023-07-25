package com.task.HRPORTAL.controller;

import com.task.HRPORTAL.dto.*;
import com.task.HRPORTAL.entity.Employee;
import com.task.HRPORTAL.entity.TimingRecords;
import com.task.HRPORTAL.exception.AccessDeniedException;
import com.task.HRPORTAL.exception.TimingRecordServiceException;
import com.task.HRPORTAL.repo.LogOutRepo;
import com.task.HRPORTAL.service.TimingRecordsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
//@Slf4j
@RequestMapping("/hrPortal")
public class TimingRecordController {


//    Logger logger = LoggerFactory.getLogger(TimingRecordController.class);
    @Autowired
    private TimingRecordsService timingRecordsService;

    @Autowired
    private LogOutRepo logOutRepo;

    @PostMapping("/loggedIn")
    public ResponseEntity<?> loggedIn(@RequestBody LoginDto login) throws TimingRecordServiceException {
        try {
            return ResponseEntity.ok(timingRecordsService.login(login));
        }catch (TimingRecordServiceException ex){
            throw new TimingRecordServiceException(String.valueOf(ex), HttpStatus.FORBIDDEN.value());
        }
    }

    @PostMapping("/loggedOut")
    public ResponseEntity<String> loggedOut(@RequestBody LogOutDto logOut) throws TimingRecordServiceException {
        try {
            return ResponseEntity.ok(timingRecordsService.logout(logOut));
        }catch (Exception ex){
            throw new TimingRecordServiceException(String.valueOf(ex),HttpStatus.BAD_REQUEST.value());
        }
    }

    @PreAuthorize("hasAnyAuthority('HR','MANAGER')")
    @GetMapping("/totalWork")
    public ResponseEntity<String> totalWorkInADay(
            @RequestParam("empId") int empId,
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        try {
            String result = timingRecordsService.totalWorkingHoursInADay(empId, Optional.ofNullable(date));
            return ResponseEntity.ok(result);
        } catch (TimingRecordServiceException ex) {
            return new ResponseEntity<>("Error while calculating total work: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/leaveApplication")
    public ResponseEntity<String> appliedForALeave(@RequestBody LeaveDto leaveDto) throws TimingRecordServiceException {
        try {
            return ResponseEntity.ok(timingRecordsService.leaveApply(leaveDto));
        }catch (Exception ex){
            throw ex;
        }
    }

    @PreAuthorize("hasAnyAuthority('HR','MANAGER')")
    @PutMapping("/approval")
    public ResponseEntity<String> Approval(
            @RequestParam("empId")int empId,
            @RequestParam(value = "date",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws TimingRecordServiceException {
        try {
            return ResponseEntity.ok(timingRecordsService.leaveApproval(empId, date));
        }catch (Exception ex){
            throw ex;
        }
    }

    @PreAuthorize("hasAnyAuthority('HR','MANAGER')")
    @PutMapping("/rejected")
    public ResponseEntity<?> rejected(@RequestParam("empId") int empId,
                                      @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws TimingRecordServiceException {
        try {
            return ResponseEntity.ok(timingRecordsService.leaveRejected(empId, date));
        } catch (TimingRecordServiceException ex) {
            throw ex;
        }
    }

    @ExceptionHandler(NumberFormatException.class)
    public String handleNumberFormatException(NumberFormatException ex) {
        return "Invalid empId . Please provide a valid positive integer value for empId.";
    }

    @PreAuthorize("hasAnyAuthority('HR','MANAGER')")
    @GetMapping("/employeesWithMoreThan8Hours")
    public ResponseEntity<List<Employee>> getEmployeesWorkMoreThan8Hours(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws TimingRecordServiceException {
        try {
            return ResponseEntity.ok(timingRecordsService.moreThan8HoursWork(date));
        }catch(TimingRecordServiceException ex){
            throw   ex;
        }

    }

}
