package com.hita.shifttracker.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    // is saturday

    // is sunday

    // is holliday
}
