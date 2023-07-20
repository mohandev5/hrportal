package com.task.HRPORTAL.repo;

import com.task.HRPORTAL.entity.TimingRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface TimingRecordsRepo extends JpaRepository<TimingRecords,Integer> {
    TimingRecords findStatusByEmployeeEmpIdAndDate(int empId, Optional<Date> date);

    @Query(value = " select emp_id,log_in,log_out from timings where date = :date",nativeQuery = true)
    TimingRecords findTimingRecordsByDate(Date date);

    TimingRecords findStatusByDate(Date date);

}
