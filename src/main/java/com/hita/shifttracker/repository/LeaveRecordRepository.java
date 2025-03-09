package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.LeaveRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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


}
