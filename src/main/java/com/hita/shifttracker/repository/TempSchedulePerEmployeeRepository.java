package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.TempSchedulePerEmployeeView;
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

    public List<TempSchedulePerEmployee> findAllByAppUserIdAndMonthAndYear(int appUserId, int month, int year) {
        String sql = "SELECT * FROM temp_schedule_per_employee WHERE app_user_id = ? AND MONTH(work_date) = ? AND YEAR(work_date) = ?";

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
        }, appUserId, month, year);
    }

    // find all by app user id and month and year pivot
    public List<TempSchedulePerEmployeeView> findAllByAppUserIdAndMonthAndYearPivot(int appUserId, int month, int year) {
        String sql = "SELECT * FROM vw_temp_schedule_per_employee_view WHERE app_user_id = ? AND month = ? AND year = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TempSchedulePerEmployeeView tempSchedulePerEmployeeView = new TempSchedulePerEmployeeView();

            tempSchedulePerEmployeeView.setCategory(rs.getString("category"));
            tempSchedulePerEmployeeView.setYear(rs.getInt("year"));
            tempSchedulePerEmployeeView.setMonth(rs.getInt("month"));
            tempSchedulePerEmployeeView.setAppUserId(rs.getInt("app_user_id"));
            tempSchedulePerEmployeeView.setDay01(rs.getString("1"));
            tempSchedulePerEmployeeView.setDay02(rs.getString("2"));
            tempSchedulePerEmployeeView.setDay03(rs.getString("3"));
            tempSchedulePerEmployeeView.setDay04(rs.getString("4"));
            tempSchedulePerEmployeeView.setDay05(rs.getString("5"));
            tempSchedulePerEmployeeView.setDay06(rs.getString("6"));
            tempSchedulePerEmployeeView.setDay07(rs.getString("7"));
            tempSchedulePerEmployeeView.setDay08(rs.getString("8"));
            tempSchedulePerEmployeeView.setDay09(rs.getString("9"));
            tempSchedulePerEmployeeView.setDay10(rs.getString("10"));
            tempSchedulePerEmployeeView.setDay11(rs.getString("11"));
            tempSchedulePerEmployeeView.setDay12(rs.getString("12"));
            tempSchedulePerEmployeeView.setDay13(rs.getString("13"));
            tempSchedulePerEmployeeView.setDay14(rs.getString("14"));
            tempSchedulePerEmployeeView.setDay15(rs.getString("15"));
            tempSchedulePerEmployeeView.setDay16(rs.getString("16"));
            tempSchedulePerEmployeeView.setDay17(rs.getString("17"));
            tempSchedulePerEmployeeView.setDay18(rs.getString("18"));
            tempSchedulePerEmployeeView.setDay19(rs.getString("19"));
            tempSchedulePerEmployeeView.setDay20(rs.getString("20"));
            tempSchedulePerEmployeeView.setDay21(rs.getString("21"));
            tempSchedulePerEmployeeView.setDay22(rs.getString("22"));
            tempSchedulePerEmployeeView.setDay23(rs.getString("23"));
            tempSchedulePerEmployeeView.setDay24(rs.getString("24"));
            tempSchedulePerEmployeeView.setDay25(rs.getString("25"));
            tempSchedulePerEmployeeView.setDay26(rs.getString("26"));
            tempSchedulePerEmployeeView.setDay27(rs.getString("27"));
            tempSchedulePerEmployeeView.setDay28(rs.getString("28"));
            tempSchedulePerEmployeeView.setDay29(rs.getString("29"));
            tempSchedulePerEmployeeView.setDay30(rs.getString("30"));
            tempSchedulePerEmployeeView.setDay31(rs.getString("31"));

            return tempSchedulePerEmployeeView;
        }, appUserId, month, year);
    }
}
