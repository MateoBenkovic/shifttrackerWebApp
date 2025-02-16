package com.hita.shifttracker.repository;

import com.hita.shifttracker.dto.AppUserDTO;
import com.hita.shifttracker.dto.WorkingTimeItemDTO;
import com.hita.shifttracker.dto.WorkingTimeItemTotalHourDTO;
import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.model.WorkingTimeItemView;
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
    @Deprecated
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
    @Deprecated
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

    public List<WorkingTimeItemView> findAllWorkingTimeItemViewByAppUser(AppUser appUser, int month, int year) {
        String sql = "SELECT * FROM vw_working_time_user_wt_cal wtuv " +
                     "WHERE wtuv.us_code = ? AND wtuv.mjesec = ? AND wtuv.godina = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            WorkingTimeItemView workingTimeItemView = new WorkingTimeItemView();

            workingTimeItemView.setYear(rs.getInt("godina"));
            workingTimeItemView.setMonth(rs.getInt("mjesec"));
            workingTimeItemView.setAppUserCode(rs.getString("us_code"));
            workingTimeItemView.setIdWorkTypes(rs.getInt("id_v_r"));
            workingTimeItemView.setWorkTypeName(rs.getString("naziv_vr_rada"));

            workingTimeItemView.setDay01(rs.getInt("1"));
            workingTimeItemView.setDay02(rs.getInt("2"));
            workingTimeItemView.setDay03(rs.getInt("3"));
            workingTimeItemView.setDay04(rs.getInt("4"));
            workingTimeItemView.setDay05(rs.getInt("5"));
            workingTimeItemView.setDay06(rs.getInt("6"));
            workingTimeItemView.setDay07(rs.getInt("7"));
            workingTimeItemView.setDay08(rs.getInt("8"));
            workingTimeItemView.setDay09(rs.getInt("9"));
            workingTimeItemView.setDay10(rs.getInt("10"));
            workingTimeItemView.setDay11(rs.getInt("11"));
            workingTimeItemView.setDay12(rs.getInt("12"));
            workingTimeItemView.setDay13(rs.getInt("13"));
            workingTimeItemView.setDay14(rs.getInt("14"));
            workingTimeItemView.setDay15(rs.getInt("15"));
            workingTimeItemView.setDay16(rs.getInt("16"));
            workingTimeItemView.setDay17(rs.getInt("17"));
            workingTimeItemView.setDay18(rs.getInt("18"));
            workingTimeItemView.setDay19(rs.getInt("19"));
            workingTimeItemView.setDay20(rs.getInt("20"));
            workingTimeItemView.setDay21(rs.getInt("21"));
            workingTimeItemView.setDay22(rs.getInt("22"));
            workingTimeItemView.setDay23(rs.getInt("23"));
            workingTimeItemView.setDay24(rs.getInt("24"));
            workingTimeItemView.setDay25(rs.getInt("25"));
            workingTimeItemView.setDay26(rs.getInt("26"));
            workingTimeItemView.setDay27(rs.getInt("27"));
            workingTimeItemView.setDay28(rs.getInt("28"));
            workingTimeItemView.setDay29(rs.getInt("29"));
            workingTimeItemView.setDay30(rs.getInt("30"));
            workingTimeItemView.setDay31(rs.getInt("31"));

            workingTimeItemView.setTotal(rs.getInt("Ukupno"));

            return workingTimeItemView;
        }, appUser.getAppUserCode(), month, year);
    }
}
