package com.task.HRPORTAL.dto;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "timings")
public class LogOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timing_id")
    private int timingId;

    @Column(name = "emp_id")
    private int empId;

    @Column(name = "log_out")
    private Time logOut;

    @Column(name = "date")
    private Date date;

    public LogOut(int timingId, int empId, Time logOut, Date date) {
        this.timingId = timingId;
        this.empId = empId;
        this.logOut = logOut;
        this.date = date;
    }

    public LogOut() {
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

    public Time getLogOut() {
        return logOut;
    }

    public void setLogOut(Time logOut) {
        this.logOut = logOut;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogOut logOut1 = (LogOut) o;
        return timingId == logOut1.timingId && empId == logOut1.empId && Objects.equals(logOut, logOut1.logOut) && Objects.equals(date, logOut1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timingId, empId, logOut, date);
    }

    @Override
    public String toString() {
        return "LogOut{" +
                "timingId=" + timingId +
                ", empId=" + empId +
                ", logOut=" + logOut +
                ", date=" + date +
                '}';
    }

}
