package com.task.HRPORTAL.service;

import com.task.HRPORTAL.entity.Employee;
import com.task.HRPORTAL.entity.SecurityUser;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    public EmployeeRepo employeeRepo;

    @Autowired
    public SecurityUserRepo securityUserRepo;

    Logger logger = LoggerFactory.getLogger(getClass());


    public List<Employee> employeeList( String Authorization) throws EmployeeServiceException {
        try {
            return employeeRepo.findAll();
        }catch(Exception ex){
            throw new EmployeeServiceException("error occurred while retrieve the employee list"+ex.getMessage());
        }
    }


    public Page<Employee> getAllDetails(int pageNumber, int pageSize, String sortAttribute) throws EmployeeServiceException {
        try {
            if (pageNumber < 0 || pageSize < 0 || sortAttribute.isEmpty()) {
                throw new EmployeeServiceException("Please provide the required properties: pageNumber, pageSize, and sortAttribute.");
            }
            List<String> allowedSortAttributes = Arrays.asList("name", "email", "designation");
            if (!allowedSortAttributes.contains(sortAttribute)) {
                throw new EmployeeServiceException("Invalid sort attribute. Allowed attributes: name, email, designation");
            }
            try {
                Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortAttribute));
                return employeeRepo.findAll(pageable);
            } catch (Exception ex) {
                throw new EmployeeServiceException("Error occurred while retrieving employee details by sorting: " + ex.getMessage());
            }
        }catch (Exception ex){
            logger.error("problem in server"+ex.getMessage());
            throw new EmployeeServiceException("Problem in server");
        }
    }

    public String addNewEmployee(Employee employee) throws EmployeeServiceException {
        try {
            employeeRepo.save(employee);
            return "new employee was added";
        }catch(Exception ex){
            throw new EmployeeServiceException("error occurred while adding an employee"+ex.getMessage());
        }
    }

}
