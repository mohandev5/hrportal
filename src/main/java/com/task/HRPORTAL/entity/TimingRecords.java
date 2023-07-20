package com.task.HRPORTAL.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "timings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
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

}
