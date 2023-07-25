package com.task.HRPORTAL.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EmployeeDto {
    private String name;

    private String email;

    private String designation;

}
