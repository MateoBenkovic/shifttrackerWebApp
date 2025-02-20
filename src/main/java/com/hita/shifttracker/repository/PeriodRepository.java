package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.Period;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PeriodRepository {

    private final JdbcTemplate jdbcTemplate;

    public PeriodRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Period> findAll() {
        String sql = "SELECT * FROM period";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Period period = new Period();

            period.setYear(rs.getInt("year"));
            period.setMonth(rs.getInt("month"));
            period.setMonthName(rs.getString("month_name"));
            period.setClosingDate(rs.getDate("closing_date") != null ? rs.getDate("closing_date").toLocalDate() : null);
            period.setStartDate(rs.getDate("start_date") != null ? rs.getDate("start_date").toLocalDate() : null);
            period.setEndDate(rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null);
            period.setTotalHours(rs.getInt("total_hours"));
            period.setPayrollBaseMonthly(rs.getBigDecimal("payroll_base_monthly"));
            period.setPayrollBaseHourly(rs.getBigDecimal("payroll_base_hourly"));
            period.setWorkingDays(rs.getInt("working_days"));

            return period;
        });
    }
}
