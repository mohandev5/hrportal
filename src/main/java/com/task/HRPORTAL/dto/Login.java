package com.task.HRPORTAL.dto;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "timings")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timing_id")
    private int timingId;

    @Column(name = "emp_id")
    private int empId;

    @Column(name = "log_in")
    private Time logIn;

    @Column(name = "date")
    private Date date;


    @Column(name = "status")
    private String status;

    public Login(int timingId, int empId, Time logIn, Date date, String status) {
        this.timingId = timingId;
        this.empId = empId;
        this.logIn = logIn;
        this.date = date;
        this.status = status;
    }

    public Login() {
    }

    public int getTimingId() {
        return timingId;
    }

    public void setTimingId(int timingId) {
        this.timingId = timingId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public Time getLogIn() {
        return logIn;
    }

    public void setLogIn(Time logIn) {
        this.logIn = logIn;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Login{" +
                "timingId=" + timingId +
                ", empId=" + empId +
                ", logIn=" + logIn +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }

}
