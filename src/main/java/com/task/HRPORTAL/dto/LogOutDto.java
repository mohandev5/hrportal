package com.task.HRPORTAL.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.sql.Time;
import java.util.Date;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class LogOutDto {

    private int empId;

    private Time logOut;

    private Date date;
}
