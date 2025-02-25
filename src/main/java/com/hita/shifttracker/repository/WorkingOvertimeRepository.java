package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.WorkingOvertime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkingOvertimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public WorkingOvertimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WorkingOvertime> findAll() {
        String sql = "SELECT * FROM working_overtime";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            WorkingOvertime workingOvertime = new WorkingOvertime();

            workingOvertime.setIdOvertime(rs.getInt("id_overtime"));
            workingOvertime.setIdWorkTime(rs.getInt("id_work_time"));
            workingOvertime.setAppUserId(rs.getInt("app_user_id"));
            workingOvertime.setDateFrom(rs.getDate("date_from").toLocalDate());
            workingOvertime.setHoursFrom(rs.getBigDecimal("hours_from"));
            workingOvertime.setDateTo(rs.getDate("date_to").toLocalDate());
            workingOvertime.setHoursTo(rs.getBigDecimal("hours_to"));
            workingOvertime.setShiftId(rs.getInt("shift_id"));
            workingOvertime.setStatus(rs.getString("status"));

            return workingOvertime;
        });
    }
}
