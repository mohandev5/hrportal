package com.task.HRPORTAL.controller;
import com.task.HRPORTAL.entity.Employee;
import com.task.HRPORTAL.exception.EmployeeServiceException;
import com.task.HRPORTAL.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@Slf4j
@RequestMapping("/hrPortal")
public class EmployeeController {


//    Logger logger = (Logger) LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/list")
    public ResponseEntity<List<Employee>> getAll() throws EmployeeServiceException {
        return ResponseEntity.ok(employeeService.employeeList());
    }

    @GetMapping("/sorting")
    public ResponseEntity<Page<Employee>> sortingAllEmployees(@RequestParam("pageNumber") int pageNumber,
                                              @RequestParam("pageSize") int pageSize,
                                              @RequestParam("sortAttribute") String sortAttribute) throws EmployeeServiceException {
        return ResponseEntity.ok((Page<Employee>) employeeService.getAllDetails(pageNumber, pageSize, sortAttribute));
    }

    @PreAuthorize("hasAuthority('HR')")
    @PostMapping("addNew")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) throws EmployeeServiceException {
        return ResponseEntity.ok(employeeService.addNewEmployee(employee));
    }

}
