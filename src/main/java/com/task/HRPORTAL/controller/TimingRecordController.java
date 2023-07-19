package com.task.HRPORTAL.controller;

import com.task.HRPORTAL.dto.LogOut;
import com.task.HRPORTAL.dto.Login;
import com.task.HRPORTAL.entity.Employee;
import com.task.HRPORTAL.exception.TimingRecordServiceException;
import com.task.HRPORTAL.repo.LogOutRepo;
import com.task.HRPORTAL.service.TimingRecordsService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Date;
import java.util.List;


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
    public ResponseEntity<String> loggedIn(@RequestBody Login login) throws TimingRecordServiceException {
        return ResponseEntity.ok(timingRecordsService.login(login));
    }

    @PostMapping("/loggedOut")
    public ResponseEntity<String> loggedOut(@RequestBody LogOut logOut) throws TimingRecordServiceException {
        return ResponseEntity.ok(timingRecordsService.logout(logOut));
    }

    @PreAuthorize("hasAnyAuthority('HR','MANAGER')")
    @GetMapping("/totalWork")
    public ResponseEntity<Duration> totalWorkInADay(
            @RequestParam("empId")int empId,
            @RequestParam("date")@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws TimingRecordServiceException {
        return  ResponseEntity.ok(timingRecordsService.totalWorkingHoursInADay(empId, date));
    }

    @PostMapping("/leaveApplication")
    public ResponseEntity<String> appliedForALeave(@RequestBody Login login) throws TimingRecordServiceException {
        return ResponseEntity.ok(timingRecordsService.leaveApply(login));
    }


    @PreAuthorize("hasAnyAuthority('HR','MANAGER')")
    @PutMapping("/approval")
    public ResponseEntity<String> Approval(
            @RequestParam("empId")int empId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws TimingRecordServiceException {
        return ResponseEntity.ok(timingRecordsService.leaveApproval(empId, date));
    }

    @PreAuthorize("hasAnyAuthority('HR','MANAGER')")
    @PutMapping("/rejected")
    public ResponseEntity<String> rejected(@RequestParam("empId")int empId,
                           @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")Date date) throws TimingRecordServiceException {
        return ResponseEntity.ok(timingRecordsService.leaveRejected(empId, date));
    }

    @PreAuthorize("hasAnyAuthority('HR','MANAGER')")
    @GetMapping("/employeesWithMoreThan8Hours")
    public ResponseEntity<List<Employee>> getEmployeesWorkMoreThan8Hours(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws TimingRecordServiceException {
//        logger.info("info"+INFO);
        return ResponseEntity.ok(timingRecordsService.moreThan8HoursWork(date));
    }


}
