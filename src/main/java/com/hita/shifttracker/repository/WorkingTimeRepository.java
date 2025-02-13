package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.WorkingTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


//@Repository
//public class WorkingTimeRepository {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    public void insertOrUpdateWorkingTime(int appUserId, LocalDate dateFrom, int hoursFrom, LocalDate dateTo, int hoursTo, int totalHours, int shiftId,
//                                          int updateHoursFrom, int updateHoursTo, int updateTotalHours) {
//        String sqlWorkingTime = "INSERT INTO working_time (app_user_id, date_from, hours_from, date_to, hours_to, total_hours, shift_id) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
//                "ON CONFLICT (app_user_id, date_from, date_to) " +
//                "DO UPDATE SET hours_from = ?, hours_to = ?, total_hours = ?"; // shift_id
//
//        jdbcTemplate.update(sqlWorkingTime, appUserId, dateFrom, hoursFrom, dateTo, hoursTo, totalHours, shiftId,
//                updateHoursFrom, updateHoursTo, updateTotalHours);
//
//    }
//}

public interface WorkingTimeRepository extends CrudRepository<WorkingTime, Integer> {
//
//    @Query("INSERT INTO working_time (app_user_id, date_from, hours_from, date_to, hours_to, total_hours, shift_id) " +
//            "VALUES (:appUserId, :dateFrom, :hoursFrom, :dateTo, :hoursTo, :totalHours, :shiftId) " +
//            "ON CONFLICT (app_user_id, date_from, date_to) " +
//            "DO UPDATE SET hours_from = :hoursFrom, hours_to = :hoursTo, total_hours = :totalHours, shift_id = :shiftId")
//    void upsertWorkingTime(Long appUserId, LocalDate dateFrom, BigDecimal hoursFrom, LocalDate dateTo, BigDecimal hoursTo, BigDecimal totalHours, Long shiftId);

    // ????

//    WITH data AS (
//            SELECT
//                    NEW.id_work_time AS id_work_time,
//            NEW.app_user_id AS app_user_id,
//            NEW.date_from AS date_from,
//            NEW.date_to AS date_to,
//            NEW.hours_from AS hours_from,
//            NEW.hours_to AS hours_to,
//            NEW.total_hours AS total_hours,
//        (NEW.date_to - NEW.date_from)::INTEGER AS day_count
//),
//    numbers AS (
//            SELECT generate_series(0, 10) AS number
//),
//    intervals AS (
//            SELECT
//                    p.*,
//            p.date_from + v.number * INTERVAL '1 day' AS date,
//            CASE
//                    WHEN v.number = 0 THEN p.hours_from
//                    ELSE 0
//                    END AS start,
//            CASE
//                    WHEN v.number = p.day_count THEN p.hours_to
//                    ELSE 24
//                    END AS end
//                    FROM data p, numbers v
//    WHERE v.number <= p.day_count
//    ),
//    shift_overlap AS (
//            SELECT
//                    gi.id_work_time,
//            gi.app_user_id,
//            gi.date,
//            os.shift_code,
//            CASE
//                    WHEN os.hours_to < os.hours_from THEN
//                    CASE
//                    WHEN gi.start >= os.hours_from THEN gi.start
//                    WHEN gi.start < os.hours_to THEN gi.start
//                    ELSE os.hours_from
//                    END
//                    ELSE
//                    CASE
//                    WHEN gi.start > os.hours_from THEN gi.start
//                    ELSE os.hours_from
//                    END
//                    END AS effective_start,
//            CASE
//                    WHEN os.hours_to < os.hours_from THEN
//                    CASE
//                    WHEN gi.end <= os.hours_to THEN gi.end
//                    WHEN gi.end > os.hours_from THEN gi.end
//                    ELSE os.hours_to
//                    END
//                    ELSE
//                    CASE
//                    WHEN gi.end < os.hours_to THEN gi.end
//                    ELSE os.hours_to
//                    END
//                    END AS effective_end
//                    FROM intervals gi
//                    CROSS JOIN payroll_shifts os
//                    WHERE
//                    (
//                    (os.hours_to > os.hours_from AND gi.start < os.hours_to AND gi.end > os.hours_from)
//    OR (os.hours_to < os.hours_from AND (gi.start < os.hours_to OR gi.end > os.hours_from))
//            )
//            ),
//    day_identification AS (
//            SELECT
//                    ps.id_work_time,
//            ps.app_user_id,
//            ps.date,
//            ps.shift_code,
//            ps.effective_start,
//            ps.effective_end,
//            CASE
//                    WHEN EXISTS (SELECT 1 FROM Holiday h WHERE h.date = ps.date) THEN 'D'
//    ELSE 'N'
//    END AS holiday_flag,
//    CASE
//    WHEN TO_CHAR(ps.date, 'Day') LIKE 'Saturday%' THEN 'S'
//    WHEN TO_CHAR(ps.date, 'Day') LIKE 'Sunday%' THEN 'N'
//    ELSE 'R'
//    END AS day_type_id
//    FROM shift_overlap ps
//    ORDER BY 1, 3, 5
//            ),
//    work_type_connection AS (
//            SELECT
//                    idd.id_work_time,
//            idd.app_user_id,
//            idd.date,
//            idd.shift_code,
//            idd.effective_start,
//            idd.effective_end,
//            idd.holiday_flag,
//            idd.day_type_id,
//            wt.id AS work_type_code,
//            wt.work_type_name,
//            wt.coefficient
//                    FROM day_identification idd
//                    LEFT JOIN Work_Types wt
//                    ON wt.shift_indicator = idd.shift_code
//                    AND wt.day_indicator = idd.day_type_id
//                    AND wt.holiday_indicator = idd.holiday_flag
//                    AND wt.overtime_indicator = 'N'
//    )
//-- Attempt to insert the data, or update if the combination exists
//    INSERT INTO working_time_item (work_time_id, app_user_id, date, work_type_code, hours_from, hours_to, total_hours, item_number)
//    SELECT
//    wtr.id_work_time,
//    wtr.app_user_id,
//    wtr.date,
//    wtr.work_type_code,
//    wtr.effective_start,
//    wtr.effective_end,
//    ABS(wtr.effective_start - wtr.effective_end) AS total_hours,
//    ROW_NUMBER() OVER (PARTITION BY wtr.id_work_time ORDER BY wtr.date, wtr.effective_start) AS item_number
//    FROM work_type_connection wtr
//    WHERE wtr.effective_end > wtr.effective_start
//    ON CONFLICT (app_user_id, date, item_number)
//    DO UPDATE
//    SET
//            work_time_id = EXCLUDED.work_time_id,
//            work_type_code = EXCLUDED.work_type_code,
//            hours_from = EXCLUDED.hours_from,
//            hours_to = EXCLUDED.hours_to,
//            total_hours = EXCLUDED.total_hours;

    // ????


    @Transactional
    @Modifying
    @Query("INSERT INTO working_time (app_user_id, date_from, hours_from, date_to, hours_to, total_hours, shift_id) " +
            "VALUES (:appUserId, :dateFrom, :hoursFrom, :dateTo, :hoursTo, :totalHours, :shiftId) " +
            "ON CONFLICT (app_user_id, date_from, date_to) " +
            "DO UPDATE SET hours_from = :hoursFrom, hours_to = :hoursTo, total_hours = :totalHours, shift_id = :shiftId")
    void insertOrUpdateWorkingTime(int appUserId, LocalDate dateFrom, int hoursFrom, LocalDate dateTo, int hoursTo, int totalHours, int shiftId);
}
