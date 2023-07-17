package com.task.HRPORTAL.controller;

import com.task.HRPORTAL.entity.Employee;
import com.task.HRPORTAL.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/list")
    public List<Employee> getAll(){
        return employeeService.employeeList();
    }

    //sorting the employee details by our required attribute
    @GetMapping("/sorting/{pageNumber}/{pageSize}/{sortAttribute}")
    public Page<Employee> getAll(@PathVariable("pageNumber")int pageNumber, @PathVariable("pageSize")int pageSize, @PathVariable("sortAttribute")String sortAttribute){
        return (Page<Employee>) employeeService.getAllDetails(pageNumber,pageSize,sortAttribute);
    }
}
