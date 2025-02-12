package com.hita.shifttracker.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public class WorkingTimeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertOrUpdateWorkingTime(int appUserId, LocalDate dateFrom, int hoursFrom, LocalDate dateTo, int hoursTo, int totalHours, int shiftId) {
        String sqlWorkingTime = "INSERT INTO working_time (app_user_id, date_from, hours_from, date_to, hours_to, total_hours, shift_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (app_user_id, date_from, date_to) " +
                "DO UPDATE SET hours_from = ?, hours_to = ?, total_hours = ?, shift_id = ?";

        // Execute the query using JdbcTemplate (no result expected)
        jdbcTemplate.update(sqlWorkingTime, appUserId, dateFrom, hoursFrom, dateTo, hoursTo, totalHours, shiftId, hoursFrom, hoursTo, totalHours, shiftId);

    }

}
