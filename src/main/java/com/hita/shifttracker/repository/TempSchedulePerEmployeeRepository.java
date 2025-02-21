package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.TempSchedulePerEmployee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TempSchedulePerEmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    public TempSchedulePerEmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TempSchedulePerEmployee> findAll() {
        String sql = "SELECT * FROM temp_schedule_per_employee";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TempSchedulePerEmployee tempSchedulePerEmployee = new TempSchedulePerEmployee();

            tempSchedulePerEmployee.setId(rs.getInt("id"));
            tempSchedulePerEmployee.setWorkDate(rs.getDate("work_date").toLocalDate());
            tempSchedulePerEmployee.setHoursFrom(rs.getInt("hours_from"));
            tempSchedulePerEmployee.setHoursTo(rs.getInt("hours_to"));
            tempSchedulePerEmployee.setAppUserId(rs.getInt("app_user_id"));
            tempSchedulePerEmployee.setShiftId(rs.getInt("shift_id"));
            tempSchedulePerEmployee.setStavka(rs.getInt("stavka"));

            return tempSchedulePerEmployee;
        });
    }
}
