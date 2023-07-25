package com.task.HRPORTAL.service;

import com.task.HRPORTAL.dto.EmployeeDto;
import com.task.HRPORTAL.entity.Employee;
import com.task.HRPORTAL.exception.EmployeeServiceException;
import com.task.HRPORTAL.repo.EmployeeRepo;
import com.task.HRPORTAL.repo.SecurityUserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService  {

    @Autowired
    public EmployeeRepo employeeRepo;

    @Autowired
    public SecurityUserRepo securityUserRepo;

    Logger logger = LoggerFactory.getLogger(getClass());


    public List<Employee> employeeList() throws EmployeeServiceException {
        try {
            List<Employee> employeeList = employeeRepo.findAll();
            if (employeeList.isEmpty()) {
                throw new EmployeeServiceException("the employee list is completely empty");
            }
            return employeeList;
        }catch (Exception ex){
            throw new EmployeeServiceException("someThing went wrong in service layer while retrieving the employeeList"+ex.getMessage());
        }
    }


    public Page<Employee> getAllDetails(int pageNumber, int pageSize, String sortAttribute) throws EmployeeServiceException {
        try {
            if (sortAttribute.isEmpty()) {
                throw new EmployeeServiceException("Please provide the required properties: pageNumber, pageSize, and sortAttribute.",HttpStatus.BAD_REQUEST.value());
            }
            List<String> allowedSortAttributes = Arrays.asList("name", "email", "designation");
            if (!allowedSortAttributes.contains(sortAttribute)) {
                throw new EmployeeServiceException("Invalid sort attribute. Allowed attributes: name, email, designation",HttpStatus.BAD_REQUEST.value());
            }
            try {
                Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortAttribute));
                return employeeRepo.findAll(pageable);
            } catch (Exception ex) {
                throw new EmployeeServiceException("Error occurred while retrieving employee details by sorting: " + ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
        catch (NumberFormatException ex){
            throw new EmployeeServiceException("please provide valid pageNumber and pageSize", ex.getMessage(),HttpStatus.BAD_REQUEST.value());
        }
    }

    public String addNewEmployee(EmployeeDto employeeDto) throws EmployeeServiceException {
        if (employeeDto.getName() == null || employeeDto.getName().isEmpty()) {
            throw new EmployeeServiceException("Please fill in a proper name while adding", HttpStatus.BAD_REQUEST.value());
        }

        if (employeeDto.getEmail() == null || employeeDto.getEmail().isEmpty()) {
            throw new EmployeeServiceException("Please provide a proper email", HttpStatus.BAD_REQUEST.value());
        }

        if (employeeDto.getDesignation() == null || employeeDto.getDesignation().isEmpty()) {
            throw new EmployeeServiceException("Please provide a proper designation", HttpStatus.BAD_REQUEST.value());
        }

        try {
            Employee employee = new Employee();
            employee.setName(employeeDto.getName());
            employee.setEmail(employeeDto.getEmail());
            employee.setDesignation(employeeDto.getDesignation());
            employeeRepo.save(employee);
            return "New employee added";
        } catch (Exception ex) {
            throw new EmployeeServiceException("Something went wrong in the service layer: " + ex.getMessage());
        }
    }


    public String removeAnEmployee(String name, int empId) throws EmployeeServiceException {
        if (name.isEmpty()) {
            throw new EmployeeServiceException("Please provide a name", HttpStatus.BAD_REQUEST.value());
        }
        List<Employee> employeeList = employeeRepo.findEmployeeByNameAndEmpId(name, empId);
        if (employeeList.isEmpty()) {
            throw new EmployeeServiceException("No employee found with the name and empId: " + name + " " + empId, HttpStatus.NOT_FOUND.value());
        }
        try {
            employeeRepo.deleteByNameAndId(name, empId);
            return "Employee removed successfully";
        } catch (Exception ex) {
            throw new EmployeeServiceException("Error in EmployeeService layer: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}
