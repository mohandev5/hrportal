package com.task.HRPORTAL.repo;

import com.task.HRPORTAL.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {

}
