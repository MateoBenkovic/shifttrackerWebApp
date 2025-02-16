package com.hita.shifttracker.repository;

import com.hita.shifttracker.dto.WorkingTimeItemDTO;
import com.hita.shifttracker.dto.WorkingTimeItemTotalHourDTO;
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
    public List<WorkingTimeItemDTO> findItemByEmployeeIdAndMonthAndYear(int appUserId, int month, int year) {
        String sql = "SELECT wti.app_user_id, wt.work_type_name, wt.work_type_num, " +
                     "wt.account_num, wti.date, wti.work_type_code, " +
                     "wti.hours_from, wti.hours_to, wti.total_hours " +
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
            workingTimeItem.setHoursFrom(rs.getInt("hours_from"));
            workingTimeItem.setHoursTo(rs.getInt("hours_to"));
            workingTimeItem.setTotalHours(rs.getInt("total_hours"));


            return workingTimeItem;
        }, appUserId, month, year);
    }

    // total hours, hours from, hours to by app user id and month and year
    public List<WorkingTimeItemTotalHourDTO> findTotalHoursByEmployeeIdAndMonthAndYear(int appUserId, int month, int year) {
        String sql = "SELECT wti.app_user_id, wti.date, " +
                     "SUM(wti.total_hours) AS total_hours_added, " +
                     "MIN(wti.hours_from) AS hours_from_min, " +
                     "MAX(wti.hours_to) AS hours_to_max " +
                     "FROM working_time_item wti " +
                     "JOIN work_types wt ON wti.work_type_code = wt.id " +
                     "WHERE wti.app_user_id = ? " +
                     "AND EXTRACT(MONTH FROM wti.date) = ? " +
                     "AND EXTRACT(YEAR FROM wti.date) = ? " +
                     "GROUP BY wti.app_user_id, wti.date " +
                     "ORDER BY wti.app_user_id, wti.date";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            WorkingTimeItemTotalHourDTO workingTimeItem = new WorkingTimeItemTotalHourDTO();

            workingTimeItem.setAppUserId(rs.getInt("app_user_id"));
            workingTimeItem.setDate(rs.getDate("date").toLocalDate());
            workingTimeItem.setTotalHours(rs.getInt("total_hours_added"));
            workingTimeItem.setHoursFrom(rs.getInt("hours_from_min"));
            workingTimeItem.setHoursTo(rs.getInt("hours_to_max"));

            return workingTimeItem;
        }, appUserId, month, year);
    }
}
