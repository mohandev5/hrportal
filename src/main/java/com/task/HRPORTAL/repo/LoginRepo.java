package com.task.HRPORTAL.repo;

import com.task.HRPORTAL.dto.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LoginRepo extends JpaRepository<Login,Integer> {

    List<Login> findLogInByEmpIdAndDate(int empId, Date date);

    List<Login> findEmpIdAndLogInByDate(Date date);
}
