package com.task.HRPORTAL.repo;

import com.task.HRPORTAL.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {

//    @Query(value = "SELECT e FROM Employee e INNER JOIN e.timingRecords tr WHERE tr.date = :date GROUP BY e.empId HAVING SUM(HOUR(TIMEDIFF(tr.logOut, tr.logIn))) > 8")
//    List<Employee> findEmployeeByDate(Date date);

}
