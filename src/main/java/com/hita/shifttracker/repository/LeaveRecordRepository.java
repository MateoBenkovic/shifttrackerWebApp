package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.LeaveRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class LeaveRecordRepository {

    private final JdbcTemplate jdbcTemplate;

    public LeaveRecordRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // app user id, work type other id, date from, date to, comment leave
    public void insert(LeaveRecord leaveRecord) {
        String sql = "INSERT INTO leave_record (app_user_id, work_type_other_id, date_from, date_to, comment_leave, " +
                "status, hours_per_day, total_days, total_hours, uid_ins_upd) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, leaveRecord.getAppUserId(), leaveRecord.getWorkTypeOtherId(), leaveRecord.getDateFrom(),
                leaveRecord.getDateTo(), leaveRecord.getCommentLeave(), leaveRecord.getStatus(), leaveRecord.getHoursPerDay(),
                leaveRecord.getTotalDays(), leaveRecord.getTotalHours(), leaveRecord.getUidInsUpd());
    }

    // find by app user id and date from

    // exist by app user id and date from
    public boolean existsByAppUserIdAndDateFrom(int appUserId, LocalDate dateFrom) {
        String sql = "SELECT COUNT(*) FROM leave_record WHERE app_user_id = ? AND date_from = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{appUserId, dateFrom}, Integer.class) > 0;
    }

    // exists by app user id and worktype other id, and date from in between date from and date to, and date to in between date from and date to
    public boolean overlapRecord(int appUserId, int workTypeOtherId, LocalDate dateFrom, LocalDate dateTo) {
        String sql = "SELECT COUNT(*) FROM leave_record WHERE app_user_id = ? AND work_type_other_id = ? AND " +
                "(date_from BETWEEN ? AND ? OR date_to BETWEEN ? AND ?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{appUserId, workTypeOtherId, dateFrom, dateTo, dateFrom, dateTo}, Integer.class) > 0;
    }

}
