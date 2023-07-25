package com.task.HRPORTAL.dto;

import lombok.*;

import java.util.Date;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class LeaveDto {

    private int empId;

    private Date date;

    private String status;
}
