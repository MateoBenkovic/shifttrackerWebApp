package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.WorkingTime;
import com.hita.shifttracker.model.WorkingTimeItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkingTimeRepository extends JpaRepository<WorkingTime, Integer> {


    //"INSERT INTO WorkingTime (dateFrom, hoursFrom, dateTo, hoursTo, totalHours, appUser, shift) "
    //           + "VALUES (:dateFrom, :hoursFrom, :dateTo, :hoursTo, :totalHours, :appUser, :shift)";
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO working_time (app_user_id, date_from, hours_from, date_to, hours_to, total_hours, shift_id) " +
            "VALUES (:appUserId, :dateFrom, :hoursFrom, :dateTo, :hoursTo, :totalHours, :shiftId) " +
            "ON CONFLICT (app_user_id, date_from, date_to) " +
            "DO UPDATE SET date_from = :dateFrom, hours_from = :hoursFrom, date_to = :dateTo, hours_to = :hoursTo, total_hours = :totalHours " +
            "RETURNING *" , nativeQuery = true)
    WorkingTime saveWorkingTime(@Param("dateFrom") LocalDate dateFrom, @Param("hoursFrom") int hoursFrom, @Param("dateTo") LocalDate dateTo
            , @Param("hoursTo") int hoursTo, @Param("totalHours") int totalHours, @Param("appUserId") int appUserId, @Param("shiftId") int shiftId);

    // get all records
    @Query(value = "SELECT * FROM working_time", nativeQuery = true)
    List<WorkingTime> findAll();

    @Query(value = "SELECT * FROM working_time WHERE app_user_id = :appUserId", nativeQuery = true)
    List<WorkingTime> findAllByAppUserId(int appUserId);

}
