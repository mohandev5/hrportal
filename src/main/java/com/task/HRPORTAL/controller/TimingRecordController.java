package com.task.HRPORTAL.controller;

import com.task.HRPORTAL.dto.LogOut;
import com.task.HRPORTAL.dto.Login;
import com.task.HRPORTAL.entity.Employee;
import com.task.HRPORTAL.repo.LogOutRepo;
import com.task.HRPORTAL.service.TimingRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@RestController
public class TimingRecordController {

    @Autowired
    private TimingRecordsService timingRecordsService;

    @Autowired
    private LogOutRepo logOutRepo;

    @PostMapping("/loggedIn")
    public String loggedIn(@RequestBody Login login){
        return timingRecordsService.login(login);
    }

    @PostMapping("/loggedOut")
    public String loggedOut(@RequestBody LogOut logOut){
        return timingRecordsService.logout(logOut);
    }

    @PreAuthorize("hasAnyAuthority('HR','MANAGER')")
    @GetMapping("/totalWork")
    public Duration totalWorkInADay(
            @RequestParam("empId")int empId,
            @RequestParam("date")@DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return  timingRecordsService.totalWorkingHoursInADay(empId, date);
    }

    @PostMapping("/leaveApplication")
    public String appliedForALeave(@RequestBody Login login){
        return timingRecordsService.leaveApply(login);
    }


    @PreAuthorize("hasAnyAuthority('HR','MANAGER')")
    @PutMapping("/approval")
    public String Approval(
            @RequestParam("empId")int empId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return timingRecordsService.leaveApproval(empId, date);
    }

    @PreAuthorize("hasAnyAuthority('HR','MANAGER')")
    @PutMapping("/rejected")
    public String rejected(@RequestParam("empId")int empId,
                           @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")Date date){
        return timingRecordsService.leaveRejected(empId, date);
    }

    @PreAuthorize("hasAnyAuthority('HR','MANAGER')")
    @GetMapping("/employeesWithMoreThan8Hours")
    public List<Employee> getEmployeesWorkMoreThan8Hours(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return timingRecordsService.moreThan8HoursWork(date);
    }



}
