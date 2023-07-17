package com.task.HRPORTAL.controller;

import com.task.HRPORTAL.dto.LogOut;
import com.task.HRPORTAL.dto.Login;
import com.task.HRPORTAL.entity.Employee;
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

    @PostMapping("login")
    public String loggedIn(@RequestBody Login login){
        return timingRecordsService.login(login);
    }

    @PostMapping("logout")
    public String loggedIn(@RequestBody LogOut logOut){
        return timingRecordsService.logout(logOut);
    }

    @PreAuthorize("hasAnyAuthority('HR','MANAGER')")
    @GetMapping("/totalWork/{empId}/{date}")
    public Duration totalWorkInADay(@PathVariable("empId")int empId, @PathVariable("date")@DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return  timingRecordsService.totalWorkingHoursInADay(empId, date);
    }

    @PostMapping("/leaveApplication")
    public String appliedForALeave(@RequestBody Login login){
        return timingRecordsService.leaveApply(login);
    }

    @PreAuthorize("hasAnyAuthority('HR','MANAGER')")
    @PutMapping("/approval/{empId}/{date}")
    public String Approval(@PathVariable("empId")int empId, @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return timingRecordsService.leaveApproval(empId, date);
    }

    @PreAuthorize("hasAnyAuthority('HR','MANAGER')")
    @PutMapping("/rejected/{empId}/{date}")
    public String rejected(@PathVariable("empId")int empId, @PathVariable("date")Date date){
        return timingRecordsService.leaveRejected(empId, date);
    }


    @PreAuthorize("hasAnyAuthority('HR','MANAGER')")
    @GetMapping("/employeesWithMoreThan8Hours/{date}")
    public List<Employee> getEmployeesWorkMoreThan8Hours(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return timingRecordsService.moreThan8HoursWork(date);
    }


}
