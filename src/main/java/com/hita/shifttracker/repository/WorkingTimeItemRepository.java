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

    public List<WorkingTimeItemView> findAllWorkingTimeItemViewByAppUser(AppUserDTO appUser, int month, int year) {
        String sql = "SELECT * FROM vw_working_tim_user_all_wt_cal wtuv " +
                     "WHERE wtuv.us_code = ? AND wtuv.mjesec = ? AND wtuv.godina = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            WorkingTimeItemView workingTimeItemView = new WorkingTimeItemView();

            workingTimeItemView.setYear(rs.getInt("godina"));
            workingTimeItemView.setMonth(rs.getInt("mjesec"));
            workingTimeItemView.setAppUserCode(rs.getString("us_code"));
            workingTimeItemView.setIdWorkTypes(rs.getInt("id_v_r"));
            workingTimeItemView.setWorkTypeName(rs.getString("naziv_vr_rada"));

            workingTimeItemView.setDay01(rs.getString("1"));
            workingTimeItemView.setDay02(rs.getString("2"));
            workingTimeItemView.setDay03(rs.getString("3"));
            workingTimeItemView.setDay04(rs.getString("4"));
            workingTimeItemView.setDay05(rs.getString("5"));
            workingTimeItemView.setDay06(rs.getString("6"));
            workingTimeItemView.setDay07(rs.getString("7"));
            workingTimeItemView.setDay08(rs.getString("8"));
            workingTimeItemView.setDay09(rs.getString("9"));
            workingTimeItemView.setDay10(rs.getString("10"));
            workingTimeItemView.setDay11(rs.getString("11"));
            workingTimeItemView.setDay12(rs.getString("12"));
            workingTimeItemView.setDay13(rs.getString("13"));
            workingTimeItemView.setDay14(rs.getString("14"));
            workingTimeItemView.setDay15(rs.getString("15"));
            workingTimeItemView.setDay16(rs.getString("16"));
            workingTimeItemView.setDay17(rs.getString("17"));
            workingTimeItemView.setDay18(rs.getString("18"));
            workingTimeItemView.setDay19(rs.getString("19"));
            workingTimeItemView.setDay20(rs.getString("20"));
            workingTimeItemView.setDay21(rs.getString("21"));
            workingTimeItemView.setDay22(rs.getString("22"));
            workingTimeItemView.setDay23(rs.getString("23"));
            workingTimeItemView.setDay24(rs.getString("24"));
            workingTimeItemView.setDay25(rs.getString("25"));
            workingTimeItemView.setDay26(rs.getString("26"));
            workingTimeItemView.setDay27(rs.getString("27"));
            workingTimeItemView.setDay28(rs.getString("28"));
            workingTimeItemView.setDay29(rs.getString("29"));
            workingTimeItemView.setDay30(rs.getString("30"));
            workingTimeItemView.setDay31(rs.getString("31"));

            workingTimeItemView.setTotal(rs.getBigDecimal("Ukupno"));

            System.out.println(workingTimeItemView);

            return workingTimeItemView;
        }, appUser.getUserCode(), month, year);
    }
}
