package com.task.HRPORTAL.controller;

import com.task.HRPORTAL.entity.Employee;
import com.task.HRPORTAL.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/list")
    public List<Employee> getAll(){
        return employeeService.employeeList();
    }

    @GetMapping("/sorting")
    public Page<Employee> sortingAllEmployees(@RequestParam("pageNumber") int pageNumber,
                                              @RequestParam("pageSize") int pageSize,
                                              @RequestParam("sortAttribute") String sortAttribute) {
        return (Page<Employee>) employeeService.getAllDetails(pageNumber, pageSize, sortAttribute);
    }

    @PreAuthorize("hasAuthority('HR')")
    @PostMapping("addNew")
    public String addEmployee(@RequestBody Employee employee){
        return employeeService.addNewEmployee(employee);
    }

}
