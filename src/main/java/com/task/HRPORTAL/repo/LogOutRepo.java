package com.task.HRPORTAL.repo;

import com.task.HRPORTAL.dto.LogOut;
import com.task.HRPORTAL.dto.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LogOutRepo extends JpaRepository<LogOut,Integer> {
    List<LogOut> findLogOutByEmpIdAndDate(int empId, Date date);

    List<LogOut> findEmpIdAndLogOutByDate(Date date);

    List<LogOut>findLogOutByEmpId(int empId);
}
