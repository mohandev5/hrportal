package com.task.HRPORTAL.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private int empId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "designation")
    private String designation;

    @OneToMany(mappedBy = "employee")
//    @JoinColumn(name = "empId")
    private List<TimingRecords> timingRecords;

    public Employee(int empId, String name, String email, String designation) {
        this.empId = empId;
        this.name = name;
        this.email = email;
        this.designation = designation;
    }

    public Employee() {
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<TimingRecords> getTimingRecords() {
        return timingRecords;
    }

    public void setTimingRecords(List<TimingRecords> timingRecords) {
        this.timingRecords = timingRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return empId == employee.empId && Objects.equals(name, employee.name) && Objects.equals(email, employee.email) && Objects.equals(designation, employee.designation) && Objects.equals(timingRecords, employee.timingRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, name, email, designation, timingRecords);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", designation='" + designation + '\'' +
                ", timingRecords=" + timingRecords +
                '}';
    }

}
