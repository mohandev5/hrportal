package com.task.HRPORTAL.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
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

}
