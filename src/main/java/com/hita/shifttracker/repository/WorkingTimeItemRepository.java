package com.hita.shifttracker.repository;

import com.hita.shifttracker.dto.WorkingTimeItemDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkingTimeItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public WorkingTimeItemRepository (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // find all by app user id and month and year
    public List<WorkingTimeItemDTO> findByEmployeeIdAndMonthAndYear(int appUserId, int month, int year) {
        String sql = "SELECT wti.app_user_id, wt.work_type_name, wt.work_type_num, " +
                     "wt.account_num, wti.date, wti.work_type_code, wti.total_hours " +
                     "FROM working_time_item wti " +
                     "JOIN work_types wt " +
                     "ON wti.work_type_code = wt.id " +
                     "WHERE wti.app_user_id = ? " +
                     "AND EXTRACT(MONTH FROM wti.date) = ? " +
                     "AND EXTRACT(YEAR FROM wti.date) = ? " +
                     "ORDER BY wt.id, wti.date";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            WorkingTimeItemDTO workingTimeItem = new WorkingTimeItemDTO();

            workingTimeItem.setAppUserId(rs.getInt("app_user_id"));
            workingTimeItem.setWorkTypeName(rs.getString("work_type_name"));
            workingTimeItem.setWorkTypeNum(rs.getString("work_type_num"));
            workingTimeItem.setAccountNum(rs.getString("account_num"));
            workingTimeItem.setDate(rs.getDate("date").toLocalDate());
            workingTimeItem.setWorkTypeCode(rs.getInt("work_type_code"));
            workingTimeItem.setTotalHours(rs.getInt("total_hours"));

            return workingTimeItem;
        }, appUserId, month, year);
    }
}
