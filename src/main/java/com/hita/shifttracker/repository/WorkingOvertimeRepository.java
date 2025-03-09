package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.WorkingOvertime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    // insert into overtime
    public void insert(WorkingOvertime workingOvertime) {
        String sql = "INSERT INTO working_overtime (id_work_time, app_user_id, date_from, hours_from, date_to," +
                "hours_to, total_hours) VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, workingOvertime.getIdWorkTime(), workingOvertime.getAppUserId(),
                workingOvertime.getDateFrom(), workingOvertime.getHoursFrom(), workingOvertime.getDateTo(),
                workingOvertime.getHoursTo(), workingOvertime.getTotalHours());
    }

    public boolean existsByAppUserIdAndDateFromAndIdWorkTime(int appUserId, LocalDate dateFrom, int idWorkTime) {
        String sql = "SELECT * FROM working_overtime WHERE app_user_id = ? AND date_from = ? AND id_work_time = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{appUserId, dateFrom, idWorkTime}, (rs, rowNum) -> true);
    }

    // find by working time id
    public List<WorkingOvertime> findByIdWorkTime(int idWorkTime) {
        String sql = "SELECT * FROM working_overtime WHERE id_work_time = ?";
        return jdbcTemplate.query(sql, new Object[]{idWorkTime}, (rs, rowNum) -> {
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

    // set status to s
    public void setStatusToS(int idOvertime, int idWorkTime, int appUserId) {
        String sql = "UPDATE working_overtime SET status = 'S', uid_ins_upd = ? WHERE id_overtime = ? AND id_work_time = ?";
        jdbcTemplate.update(sql, appUserId,idOvertime, idWorkTime);
    }
}
