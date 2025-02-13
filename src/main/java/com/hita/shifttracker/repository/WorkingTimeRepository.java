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
            "VALUES (:appUserId, :dateFrom, :hoursFrom, :dateTo, :hoursTo, :totalHours, :shiftId) " +
            "ON CONFLICT (app_user_id, date_from, date_to) " +
            "DO UPDATE SET hours_from = :hoursFrom, hours_to = :hoursTo, total_hours = :totalHours, shift_id = :shiftId, date_from = :dateFrom, date_to = :dateTo")
    void insertOrUpdateWorkingTime(int appUserId, LocalDate dateFrom, int hoursFrom, LocalDate dateTo, int hoursTo, int totalHours, int shiftId);


    @Query("SELECT * FROM working_time wt WHERE app_user_id = :appUserId AND date_from = :dateFrom")
    List<WorkingTime> findWorkingTimeByAppUserIdAndDate(int appUserId, LocalDate dateFrom);

    @Query("SELECT * FROM working_time wt WHERE app_user_id = :appUserId AND EXTRACT(MONTH FROM wt.date_from) = :month")
    List<WorkingTime> findByAppUserIdAndMonth(int appUserId, int month);
}
