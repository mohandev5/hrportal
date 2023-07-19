package com.task.HRPORTAL.service;

import com.task.HRPORTAL.entity.Employee;
import com.task.HRPORTAL.exception.EmployeeServiceException;
import com.task.HRPORTAL.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    public EmployeeRepo employeeRepo;

    public List<Employee> employeeList() throws EmployeeServiceException {
        try {
            return employeeRepo.findAll();
        }catch(Exception ex){
            throw new EmployeeServiceException("error occurred while retrieve the employee list"+ex.getMessage());
        }
    }


    public Page<Employee> getAllDetails(int pageNumber, int pageSize, String sortAttribute) throws EmployeeServiceException {
            try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortAttribute));
            return employeeRepo.findAll(pageable);
            } catch (Exception ex) {
                throw new EmployeeServiceException("error occurred while retrieve the employee details by sorting" + ex.getMessage());
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
