package com.task.HRPORTAL.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "timings")
public class TimingRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timing_id")
    private int timingId;

    @Column(name = "log_in")
    private LocalTime logIn;

    @Column(name = "log_out")
    private LocalTime logOut;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "emp_id")
    private Employee employee;

    public int getTimingId() {
        return timingId;
    }

    public void setTimingId(int timingId) {
        this.timingId = timingId;
    }

    public LocalTime getLogIn() {
        return logIn;
    }

    public void setLogIn(LocalTime logIn) {
        this.logIn = logIn;
    }

    public LocalTime getLogOut() {
        return logOut;
    }

    public void setLogOut(LocalTime logOut) {
        this.logOut = logOut;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


    public TimingRecords(int timingId, LocalTime logIn, LocalTime logOut, Date date, String status, Employee employee) {
        this.timingId = timingId;
        this.logIn = logIn;
        this.logOut = logOut;
        this.date = date;
        this.status = status;
        this.employee = employee;
    }

    public TimingRecords() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimingRecords that = (TimingRecords) o;
        return timingId == that.timingId && Objects.equals(logIn, that.logIn) && Objects.equals(logOut, that.logOut) && Objects.equals(date, that.date) && Objects.equals(status, that.status) && Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timingId, logIn, logOut, date, status, employee);
    }

    @Override
    public String toString() {
        return "TimingRecords{" +
                "timingId=" + timingId +
                ", logIn=" + logIn +
                ", logOut=" + logOut +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", employee=" + employee +
                '}';
    }


}
