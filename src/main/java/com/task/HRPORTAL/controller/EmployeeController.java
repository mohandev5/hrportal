package com.task.HRPORTAL.controller;
import com.task.HRPORTAL.dto.EmployeeDto;
import com.task.HRPORTAL.entity.Employee;
import com.task.HRPORTAL.exception.EmployeeServiceException;
import com.task.HRPORTAL.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(employeeService.employeeList());
        } catch (EmployeeServiceException ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sorting")
    public ResponseEntity<?> sortingAllEmployees(@RequestParam("pageNumber") int pageNumber,
                                              @RequestParam("pageSize") int pageSize,
                                              @RequestParam("sortAttribute") String sortAttribute) throws EmployeeServiceException {
        try {
            return ResponseEntity.ok((Page<Employee>) employeeService.getAllDetails(pageNumber, pageSize, sortAttribute));
        }catch(EmployeeServiceException ex){
            return new ResponseEntity<>(ex,HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('HR')")
    @PostMapping("addNew")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDto employee) throws EmployeeServiceException {
        try {
            return ResponseEntity.ok(employeeService.addNewEmployee(employee));
        }catch (EmployeeServiceException ex){
           return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?>deleteEmployee(@RequestParam(name = "empId",required = true) int empId,
                                                @RequestParam("name")String name) throws EmployeeServiceException {
        try{
            return ResponseEntity.ok(employeeService.removeAnEmployee(name, empId));
        }catch (EmployeeServiceException ex){
            return new ResponseEntity<>(ex,HttpStatus.BAD_REQUEST);
        }
    }
    @ExceptionHandler(NumberFormatException.class)
    public String handleNumberFormatException(NumberFormatException ex) {
        return "Invalid empId format. Please provide a valid positive integer value for empId.";
    }
}
