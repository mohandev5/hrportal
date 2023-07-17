package com.task.HRPORTAL.service;

import com.task.HRPORTAL.dto.LogOut;
import com.task.HRPORTAL.dto.Login;
import com.task.HRPORTAL.entity.Employee;
import com.task.HRPORTAL.entity.TimingRecords;
import com.task.HRPORTAL.repo.EmployeeRepo;
import com.task.HRPORTAL.repo.LogOutRepo;
import com.task.HRPORTAL.repo.LoginRepo;
import com.task.HRPORTAL.repo.TimingRecordsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TimingRecordsService {
    @Autowired
    private TimingRecordsRepo timingRecordsRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private LoginRepo loginRepo;

    @Autowired
    private LogOutRepo logOutRepo;

    public String login(Login login) {
        loginRepo.save(login);
        return "You are logged in successfully";
    }

    public String logout(LogOut logOut) {
//        System.out.println("hi");
        logOutRepo.save(logOut);
//        System.out.println("hello");
        return "you logged out successfully";
    }

    public Duration totalWorkingHoursInADay(int empId, Date date) {
        List<Login> loginlist = loginRepo.findLogInByEmpIdAndDate(empId, date);
        List<LogOut> logOutList = logOutRepo.findLogOutByEmpIdAndDate(empId, date);
        Duration totalDuration = Duration.ZERO;
        for (Login login : loginlist) {
            if (login.getLogIn() == null) {
                continue;
            }
            Time loginTime = Time.valueOf(login.getLogIn().toLocalTime());
            Duration duration = Duration.ZERO;
            Duration countDuration = Duration.ZERO;
            for (LogOut logOut : logOutList) {
                if (logOut.getLogOut() != null) {
                    Time logoutTime = Time.valueOf(logOut.getLogOut().toLocalTime());
                    countDuration = Duration.between(loginTime.toLocalTime(), logoutTime.toLocalTime());
                }
            }
            totalDuration = totalDuration.plus(countDuration);
            break;
        }
        return totalDuration;
    }


    public String leaveApply(Login login) {
        loginRepo.save(login);
        return "you are applied for leave on:" + " " + login.getDate();
    }


    public String leaveApproval(int empId, Date date) {
        TimingRecords status = timingRecordsRepo.findStatusByEmployeeEmpIdAndDate(empId, date);
        if (status != null && status.getStatus().equalsIgnoreCase("applied for a leave")) {
            status.setStatus("approved");
            timingRecordsRepo.save(status);
            return "Congratulations your leave approved";
        }
        return null;
    }


    public String leaveRejected(int empId, Date date) {
        TimingRecords status = timingRecordsRepo.findStatusByEmployeeEmpIdAndDate(empId, date);
        if (status != null && status.getStatus().equalsIgnoreCase("applied for a leave")) {
            status.setStatus("rejected");
            timingRecordsRepo.save(status);
            return "sorry your leave rejected ";
        }
        return null;
    }


    public List<Employee> moreThan8HoursWork(Date date) {
        List<Login> logins = loginRepo.findEmpIdAndLogInByDate(date);
        List<LogOut> logOuts = logOutRepo.findEmpIdAndLogOutByDate(date);
        List<Employee> employeesWithMoreThan8HoursWork = new ArrayList<>();
        for (Login login : logins) {
            int loginEmpId = login.getEmpId();
            Time loginTime = login.getLogIn();
            if (loginTime != null) {
                for (LogOut logOut : logOuts) {
                    int logoutEmpId = logOut.getEmpId();
                    if (loginEmpId == logoutEmpId) {
                        Time logoutTime = logOut.getLogOut();
                        if (logoutTime != null) {
                            Duration duration = Duration.between(loginTime.toLocalTime(), logoutTime.toLocalTime());
                            if (duration.compareTo(Duration.ofHours(8)) > 0) {
                                Employee employee = employeeRepo.findById(loginEmpId).orElse(null);
                                if (employee != null) {
                                    employeesWithMoreThan8HoursWork.add(employee);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        return employeesWithMoreThan8HoursWork;
    }

}
