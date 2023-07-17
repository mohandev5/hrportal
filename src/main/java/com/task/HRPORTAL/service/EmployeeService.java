package com.task.HRPORTAL.service;

import com.task.HRPORTAL.entity.Employee;
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

    public List<Employee> employeeList(){
        return employeeRepo.findAll();
    }


    public Page<Employee> getAllDetails(int pageNumber, int pageSize, String sortAttribute) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortAttribute));
        return employeeRepo.findAll(pageable);
    }

    public String addNewEmployee(Employee employee){
         employeeRepo.save(employee);
         return "new employee was added";
    }

}
