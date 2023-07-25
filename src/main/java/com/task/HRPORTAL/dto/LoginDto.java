package com.task.HRPORTAL.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.sql.Time;
import java.util.Date;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LoginDto {

    private int empId;

    private Time logIn;

    private Date date;
}
