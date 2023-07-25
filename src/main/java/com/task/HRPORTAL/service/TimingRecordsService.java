package com.task.HRPORTAL.service;

import com.task.HRPORTAL.dto.*;
import com.task.HRPORTAL.entity.Employee;
import com.task.HRPORTAL.entity.TimingRecords;
import com.task.HRPORTAL.exception.TimingRecordServiceException;
import com.task.HRPORTAL.repo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.*;
import java.util.*;

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

    @Autowired
    private SecurityUserRepo securityUserRepo;

    Logger logger = LoggerFactory.getLogger(getClass());



    public String login(LoginDto loginDto) throws TimingRecordServiceException {
        if(loginDto.getDate()==null){
            throw new TimingRecordServiceException("please provide date");
        }
        if(loginDto.getLogIn()==null){
            throw new TimingRecordServiceException("please provide loginTime");
        }
        try {
            Login login = new Login();
            login.setEmpId(loginDto.getEmpId());
            login.setLogIn(loginDto.getLogIn());
            login.setDate(loginDto.getDate());
            loginRepo.save(login);
            return "logged in successfully";
        }catch(Exception ex){
            throw new TimingRecordServiceException( "error occurred while login"+ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public String logout(LogOutDto logOutDto) throws TimingRecordServiceException {
        if(logOutDto.getDate()==null){
            throw new TimingRecordServiceException("please provide logOutDate");
        }
        if(logOutDto.getLogOut() == null){
            throw new TimingRecordServiceException("please provide date");
        }
        try {
            LogOut logOut = new LogOut();
            logOut.setEmpId(logOutDto.getEmpId());
            logOut.setLogOut(logOutDto.getLogOut());
            logOut.setDate(logOutDto.getDate());
            logOutRepo.save(logOut);
            return "you logged out successfully";
        }catch (Exception ex){
            logger.error("problem in server"+ex.getMessage());
            throw new TimingRecordServiceException("error occurred while logout"+ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public String totalWorkingHoursInADay(int empId, Optional<Date> date) throws TimingRecordServiceException {
//        try {
            List<Login> loginlist = loginRepo.findLogInByEmpIdAndDate(empId, date);
            List<LogOut> logOutList = logOutRepo.findLogOutByEmpIdAndDate(empId, date);
            Duration totalDuration = Duration.ZERO;

            if (empId < 0 || date.isEmpty()) {
                throw new TimingRecordServiceException( "please provide date and empId",HttpStatus.BAD_REQUEST.value());
            }

            if (loginlist.isEmpty() || logOutList.isEmpty()) {
                throw new TimingRecordServiceException("No records found for the given employeeId and date:" + empId + " " + date,HttpStatus.BAD_REQUEST.value());
            }
            try {
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
                long totalSeconds = totalDuration.getSeconds();
                long hours = totalSeconds / 3600;
                long minutes = (totalSeconds % 3600) / 60;
                long seconds = totalSeconds % 60;
                return "hours: " + hours + ", minutes: " + minutes + ", seconds: " + seconds;
            } catch (Exception ex) {
                throw new TimingRecordServiceException("error occurred while calculating total working hours:" + ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
            }

        }

    public String leaveApply(LeaveDto leaveDto) throws TimingRecordServiceException {
        if(leaveDto.getStatus().isEmpty()){
            throw new TimingRecordServiceException("please provide leave status");
        }
        if(leaveDto.getStatus()==null){
            throw new TimingRecordServiceException("please provide date");
        }
        try {
            Login login = new Login();
            login.setEmpId(leaveDto.getEmpId());
            login.setDate(leaveDto.getDate());
            login.setStatus(leaveDto.getStatus());
            loginRepo.save(login);
            return "You are applied for a leave on:" + " " + leaveDto.getDate();
        }catch (Exception ex){
            throw new TimingRecordServiceException(String.valueOf(ex),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public String leaveApproval(int empId, Date date) throws TimingRecordServiceException {
        List<TimingRecords> timingRecordsList = timingRecordsRepo.findStatusByEmployeeEmpIdAndDate(empId, date);
        if(timingRecordsList.isEmpty()){
            throw new TimingRecordServiceException("leave list is empty");
        }
        for (TimingRecords timingRecords : timingRecordsList) {
            if (timingRecords.getStatus()!=null&&timingRecords.getStatus().equalsIgnoreCase("applied for a leave")) {
                timingRecords.setStatus("approved");
                timingRecordsRepo.save(timingRecords);
                break;
            } else {
                return "empId:"+" "+empId+"is not applying leave on date:"+" "+date;
            }
        }
        return "Congratulations your leave approved";
    }



    public String leaveRejected(int empId, Date date) throws TimingRecordServiceException {
        List<TimingRecords> timingRecordsList = timingRecordsRepo.findStatusByEmployeeEmpIdAndDate(empId, date);
        if(timingRecordsList.isEmpty()){
            throw new TimingRecordServiceException("leave list is empty");
        }
        for (TimingRecords timingRecords : timingRecordsList) {
            if (timingRecords.getStatus()!=null&&timingRecords.getStatus().equalsIgnoreCase("applied for a leave")) {
                timingRecords.setStatus("rejected");
                timingRecordsRepo.save(timingRecords);
                break;
            } else {
                return "empId:"+" "+empId+"is not applying leave on date:"+" "+date;
            }
        }
        return "sorry your leave rejected";
    }


    public List<Employee> moreThan8HoursWork(Date  date) throws TimingRecordServiceException {
//        try {
            List<Login> logins = loginRepo.findEmpIdAndLogInByDate(Optional.ofNullable(date));
            List<LogOut> logOuts = logOutRepo.findEmpIdAndLogOutByDate(Optional.ofNullable(date));
            List<Employee> employeesWithMoreThan8HoursWork = new ArrayList<>();
            if (date==null) {
                throw new TimingRecordServiceException("Please provide proper date",HttpStatus.BAD_REQUEST.value());
            }
            if (logins.isEmpty() || logOuts.isEmpty()) {
                throw new TimingRecordServiceException("no one was working on this date:" + date,HttpStatus.BAD_REQUEST.value());
            }
            try {
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
            }catch (RuntimeException ex){
                throw new TimingRecordServiceException("error in server layer",HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        return employeesWithMoreThan8HoursWork;
    }


}
