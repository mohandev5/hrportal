package com.task.HRPORTAL.service;

import com.task.HRPORTAL.dto.LogOut;
import com.task.HRPORTAL.dto.Login;
import com.task.HRPORTAL.entity.Employee;
import com.task.HRPORTAL.entity.TimingRecords;
import com.task.HRPORTAL.exception.TimingRecordServiceException;
import com.task.HRPORTAL.repo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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


    public String login(Login login) throws TimingRecordServiceException {
        try {
            loginRepo.save(login);
            return "You are logged in successfully";
        }catch (Exception ex){
            logger.error("problem in server"+ex.getMessage());
            throw new TimingRecordServiceException("error occurred while login"+ex.getMessage());
        }
    }
    public String logout(LogOut logOut) throws TimingRecordServiceException {
        try {
            logOutRepo.save(logOut);
            return "you logged out successfully";
        }catch (Exception ex){
            logger.error("problem in server"+ex.getMessage());
            throw new TimingRecordServiceException("error occurred while logout"+ex.getMessage());
        }
    }

    public Duration totalWorkingHoursInADay(int empId, Optional<Date> date) throws TimingRecordServiceException {
//        try {
            List<Login> loginlist = loginRepo.findLogInByEmpIdAndDate(empId, date);
            List<LogOut> logOutList = logOutRepo.findLogOutByEmpIdAndDate(empId, date);
            Duration totalDuration = Duration.ZERO;

            if (empId < 0 || date.isEmpty()) {
                throw new TimingRecordServiceException("please provide date and empId");
            }

            if (loginlist.isEmpty() || logOutList.isEmpty()) {
                throw new TimingRecordServiceException("No records found for the given employeeId and date:" + empId + " " + date);
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
                return totalDuration;
            } catch (Exception ex) {
                throw new RuntimeException("error occurred while calculating total working hours:" + ex.getMessage());
            }

        }
//        catch (Exception ex){
//            logger.error("problem in server"+ex.getMessage());
//            throw new TimingRecordServiceException("problem in server");
//        }
//    }

    public String leaveApply(Login login) throws TimingRecordServiceException {
        try {
            loginRepo.save(login);
            return "you are applied for leave on:" + " " + login.getDate();
        }catch (Exception ex){
            logger.error("problem in server"+ex.getMessage());
            throw new TimingRecordServiceException("error occurred while applying in leave"+ex.getMessage());
        }
    }

    public String leaveApproval(int empId, Optional<Date> date) throws TimingRecordServiceException {
//        try {
            TimingRecords status = timingRecordsRepo.findStatusByEmployeeEmpIdAndDate(empId, date);
            if (empId > 0 || date.isEmpty()) {
                throw new TimingRecordServiceException("no records found from this empId:" + empId + "on date:" + date);
            }
            try {
                if (status != null && status.getStatus().equalsIgnoreCase("applied for a leave")) {
                    status.setStatus("approved");
                    timingRecordsRepo.save(status);
                    return "Congratulations your leave approved";
                }
            } catch (Exception ex) {
                throw new TimingRecordServiceException("error occurred while approving" + ex.getMessage());
            }
//            return null;
        return null;
    }
//        catch (Exception ex){
//            logger.error("problem in server"+ex.getMessage());
//            throw new TimingRecordServiceException("problem in server");
//        }
//        return null;
//    }



    public String leaveRejected(int empId, Optional<Date> date) throws TimingRecordServiceException {
        TimingRecords status = timingRecordsRepo.findStatusByEmployeeEmpIdAndDate(empId, date);
        if(empId>0|| date.isEmpty()){
            throw new TimingRecordServiceException("no records found from this empId:"+empId+"on date:"+date);
        }
        try {
            if (status != null && status.getStatus().equalsIgnoreCase("applied for a leave")) {
                status.setStatus("rejected");
                timingRecordsRepo.save(status);
                return "sorry your leave rejected ";
            }
        }catch (Exception ex){
            throw new TimingRecordServiceException("error occurred while rejecting"+ex.getMessage());
        }
        return null;
    }

    public List<Employee> moreThan8HoursWork(Optional<Date>  date) throws TimingRecordServiceException {
//        TimingRecordsStatus result = new TimingRecordsStatus(0);
//        try {
            List<Login> logins = loginRepo.findEmpIdAndLogInByDate(date);
            List<LogOut> logOuts = logOutRepo.findEmpIdAndLogOutByDate(date);
            List<Employee> employeesWithMoreThan8HoursWork = new ArrayList<>();
            if (date.isEmpty()) {
//                result.setStatusCode(404); // Not Found
                throw new TimingRecordServiceException("Please provide proper date");
            }
            if (logins.isEmpty() || logOuts.isEmpty()) {
                throw new TimingRecordServiceException("no one was working on this date:" + date);
            } else {
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
//                if (employeesWithMoreThan8HoursWork.isEmpty()) {
//                    return (List<Employee>) new TimingRecordServiceException("no work moreThan8hours in this date:" + date);
//                }
                return employeesWithMoreThan8HoursWork;
            }
//        }catch (Exception ex){
//            logger.error("Error in moreThan8HoursWork method: {}", ex.getMessage());
//            throw new TimingRecordServiceException("problem in server");
//        }
    }

//    public List<SecurityUser> findallUsers(){
//        return securityUserRepo.findAll();
//    }


}
