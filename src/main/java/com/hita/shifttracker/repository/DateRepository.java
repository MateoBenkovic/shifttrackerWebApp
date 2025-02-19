package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.Holiday;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DateRepository {

    private final JdbcTemplate jdbcTemplate;

    public DateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getCurrentYearFromDatabase() {
        String sql = "SELECT EXTRACT(YEAR FROM CURRENT_DATE)";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int getCurrentMonthFromDatabase() {
        String sql = "SELECT EXTRACT(MONTH FROM CURRENT_DATE)";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<Holiday> getAllHolidays() {
        String sql = "SELECT * FROM holiday";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
           Holiday holiday = new Holiday();

           holiday.setHolidayId(rs.getInt("id"));
           holiday.setHolidayName(rs.getString("name"));
           holiday.setHolidayDate(rs.getDate("date").toLocalDate());

           return holiday;
        });
    }

    // is saturday

    // is sunday

    // is holliday
}
