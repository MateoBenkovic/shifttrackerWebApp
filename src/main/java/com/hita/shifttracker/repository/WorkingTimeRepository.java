package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.WorkingTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class WorkingTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public WorkingTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // without sched_id
    public void insertWorkingTimeByAppUserId(WorkingTime workingTime) {
        String sql = "INSERT INTO working_time (app_user_id, date_from, hours_from, date_to, hours_to," +
                "total_hours, shift_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, workingTime.getAppUserId(), workingTime.getDateFrom(), workingTime.getHoursFrom(),
                workingTime.getDateTo(), workingTime.getHoursTo(), workingTime.getTotalHours(), workingTime.getShiftId());
    }

    // find by app user id, date from
    public WorkingTime findByAppUserIdAndDateFrom(int appUserId, LocalDate dateFrom) {
        String sql = "SELECT wt.id_work_time, wt.app_user_id, wt.date_from, " +
                "wt.hours_from, wt.date_to, wt.hours_to, wt.total_hours, wt.shift_id " +
                "FROM working_time wt WHERE wt.app_user_id = ? AND wt.date_from = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{appUserId, dateFrom},
                (rs,rowNum) -> {
                    WorkingTime workingTime = new WorkingTime();

                    workingTime.setIdWorkTime(rs.getInt("id_work_time"));
                    workingTime.setAppUserId(rs.getInt("app_user_id"));
                    workingTime.setDateFrom(rs.getDate("date_from").toLocalDate());
                    workingTime.setHoursFrom(rs.getInt("hours_from"));
                    workingTime.setDateTo(rs.getDate("date_to").toLocalDate());
                    workingTime.setHoursTo(rs.getInt("hours_to"));
                    workingTime.setTotalHours(rs.getInt("total_hours"));
                    workingTime.setShiftId(rs.getInt("shift_id"));

                    return workingTime;
                });

    }

    // exist by app user id and date from
    public boolean existsByAppUserIdAndDateFrom(int appUserId, LocalDate dateFrom) {
        String sql = "SELECT * FROM working_time WHERE app_user_id = ? AND date_from = ?";

        // Using query to fetch a list of results (if any)
        List<WorkingTime> resultList = jdbcTemplate.query(sql, new Object[]{appUserId, dateFrom},
                (rs, rowNum) -> {
                    WorkingTime workingTime = new WorkingTime();
                    workingTime.setAppUserId(rs.getInt("app_user_id"));
                    workingTime.setDateFrom(rs.getDate("date_from").toLocalDate());
                    workingTime.setHoursFrom(rs.getInt("hours_from"));
                    workingTime.setDateTo(rs.getDate("date_to").toLocalDate());
                    workingTime.setHoursTo(rs.getInt("hours_to"));
                    workingTime.setTotalHours(rs.getInt("total_hours"));
                    workingTime.setShiftId(rs.getInt("shift_id"));
                    return workingTime;
                });

        return !resultList.isEmpty();  // Returns true if list is not empty
    }

    // find all by app user id, month and year
    public List<WorkingTime> findByAppUserIdAndMonthAndYear(int appUserId, int month, int year) {
        String sql = "SELECT * FROM working_time WHERE app_user_id = ? " +
                "AND (" +
                "(EXTRACT(MONTH FROM date_to) = ? AND EXTRACT(YEAR FROM date_to) = ?) " +
                "OR " +
                "(EXTRACT(MONTH FROM date_from) = ? AND EXTRACT(YEAR FROM date_from) = ?) " +
                ")";


        return jdbcTemplate.query(sql, new Object[]{appUserId, month, year, month, year},
                (rs,rowNum) -> {
                    WorkingTime workingTime = new WorkingTime();

                    workingTime.setIdWorkTime(rs.getInt("id_work_time"));
                    workingTime.setAppUserId(rs.getInt("app_user_id"));
                    workingTime.setDateFrom(rs.getDate("date_from").toLocalDate());
                    workingTime.setHoursFrom(rs.getInt("hours_from"));
                    workingTime.setDateTo(rs.getDate("date_to").toLocalDate());
                    workingTime.setHoursTo(rs.getInt("hours_to"));
                    workingTime.setTotalHours(rs.getInt("total_hours"));
                    workingTime.setShiftId(rs.getInt("shift_id"));

                    return workingTime;
                });
    }

    // update working time
    public void updateWorkingTimeByAppUserId(WorkingTime workingTime) {
        String sql = "UPDATE working_time " +
                "SET date_from = ?, hours_from = ?, date_to = ?, hours_to = ? " +
                "WHERE app_user_id = ? AND date_from = ?" ;

        jdbcTemplate.update(sql, workingTime.getDateFrom(), workingTime.getHoursFrom(), workingTime.getDateTo(),
                workingTime.getHoursTo(), workingTime.getAppUserId(), workingTime.getDateFrom());
    }

    // delete all
    public void deleteAll(){
        String sql = "DELETE FROM working_time";
        jdbcTemplate.update(sql);
    }

    public void deleteByAppUserIdAndDateFrom(int appUserId, LocalDate dateFrom) {
        String sql = "DELETE FROM working_time WHERE app_user_id = ? AND date_from = ?";
        int rowsAffected = jdbcTemplate.update(sql, appUserId, dateFrom);
    }
}
