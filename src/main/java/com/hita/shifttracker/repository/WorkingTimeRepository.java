package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.WorkingTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


public interface WorkingTimeRepository extends CrudRepository<WorkingTime, Integer> {

    @Query("INSERT INTO working_time (app_user_id, date_from, hours_from, date_to, hours_to, total_hours, shift_id) " +
           "VALUES (:appUserId, :dateFrom, :hoursFrom, :dateTo, :hoursTo, :totalHours, :shiftId)")
    void insertWorkingTime(int appUserId, LocalDate dateFrom, int hoursFrom, LocalDate dateTo, int hoursTo, int totalHours, int shiftId);

    @Query("SELECT wt.id_work_time, wt.app_user_id, wt.date_from, wt.hours_from, wt.date_to, wt.total_hours, wt.shift_id " +
           "FROM working_time wt WHERE wt.app_user_id = :appUserId AND wt.date_from = :dateFrom")
    WorkingTime findWorkingTimeByAppUserIdAndDateFrom(int appUserId, LocalDate dateFrom);

    @Query("UPDATE working_time wt" +
           "SET wt.date_from = :workingTime.getDateFrom(), wt.hours_from = :workingTime.getHoursFrom(), wt.date_to = :workingTime.getDateTo(), " +
           "wt.hours_to = :workingTime.getHoursTo() " +
           "WHERE wt.app_user_id = :workingTime.getAppUserId() AND wt.date_from = :workingTime.getDateFrom()")
    void updateWorkingTimeByAppUserId(WorkingTime workingTime);


}
