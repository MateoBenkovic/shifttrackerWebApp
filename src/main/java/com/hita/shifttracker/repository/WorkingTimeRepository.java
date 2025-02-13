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


//@Repository
//public class WorkingTimeRepository {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    public void insertOrUpdateWorkingTime(int appUserId, LocalDate dateFrom, int hoursFrom, LocalDate dateTo, int hoursTo, int totalHours, int shiftId,
//                                          int updateHoursFrom, int updateHoursTo, int updateTotalHours) {
//        String sqlWorkingTime = "INSERT INTO working_time (app_user_id, date_from, hours_from, date_to, hours_to, total_hours, shift_id) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
//                "ON CONFLICT (app_user_id, date_from, date_to) " +
//                "DO UPDATE SET hours_from = ?, hours_to = ?, total_hours = ?"; // shift_id
//
//        jdbcTemplate.update(sqlWorkingTime, appUserId, dateFrom, hoursFrom, dateTo, hoursTo, totalHours, shiftId,
//                updateHoursFrom, updateHoursTo, updateTotalHours);
//
//    }
//}

public interface WorkingTimeRepository extends CrudRepository<WorkingTime, Integer> {
//
//    @Query("INSERT INTO working_time (app_user_id, date_from, hours_from, date_to, hours_to, total_hours, shift_id) " +
//            "VALUES (:appUserId, :dateFrom, :hoursFrom, :dateTo, :hoursTo, :totalHours, :shiftId) " +
//            "ON CONFLICT (app_user_id, date_from, date_to) " +
//            "DO UPDATE SET hours_from = :hoursFrom, hours_to = :hoursTo, total_hours = :totalHours, shift_id = :shiftId")
//    void upsertWorkingTime(Long appUserId, LocalDate dateFrom, BigDecimal hoursFrom, LocalDate dateTo, BigDecimal hoursTo, BigDecimal totalHours, Long shiftId);


    @Transactional
    @Modifying
    @Query("INSERT INTO working_time (app_user_id, date_from, hours_from, date_to, hours_to, total_hours, shift_id) " +
            "VALUES (:appUserId, :dateFrom, :hoursFrom, :dateTo, :hoursTo, :totalHours, :shiftId) " +
            "ON CONFLICT (app_user_id, date_from, date_to) " +
            "DO UPDATE SET hours_from = :hoursFrom, hours_to = :hoursTo, total_hours = :totalHours, shift_id = :shiftId")
    void insertOrUpdateWorkingTime(int appUserId, LocalDate dateFrom, int hoursFrom, LocalDate dateTo, int hoursTo, int totalHours, int shiftId);
}
