package com.task.HRPORTAL.dto;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "timings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
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

}
