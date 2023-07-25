package com.task.HRPORTAL.repo;

import com.task.HRPORTAL.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {

//    void deleteById(int empId);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Employee e WHERE e.name = :name AND e.empId = :empId",nativeQuery = true)
    void deleteByNameAndId(String name, int empId);

    List<Employee> findEmployeeByNameAndEmpId(String name,int empId);
}
