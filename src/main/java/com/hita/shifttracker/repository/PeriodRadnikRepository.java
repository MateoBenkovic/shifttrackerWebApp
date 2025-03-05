package com.hita.shifttracker.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PeriodRadnikRepository {

    private final JdbcTemplate jdbcTemplate;

    public PeriodRadnikRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


}
